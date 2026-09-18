const API_BASE = "/api/v1";
const SESSION_KEY = "sc_session";

let currentUser = null;
let userMap = {};
let topicsList = [];
let topicsMap = {};

function userLabel(id) {
    const name = userMap[id];
    return name ? `${escapeHtml(name)} (#${id})` : `user #${id}`;
}

function capitalize(value) {
    if (!value) return "";
    return value.charAt(0).toUpperCase() + value.slice(1);
}

function escapeHtml(value) {
    if (value === null || value === undefined) return "";
    return String(value)
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;");
}

const toastEl = document.getElementById("toast");
let toastTimer = null;
function showToast(message, isError) {
    toastEl.textContent = message;
    toastEl.classList.toggle("error", !!isError);
    toastEl.classList.add("show");
    clearTimeout(toastTimer);
    toastTimer = setTimeout(() => toastEl.classList.remove("show"), 4000);
}

async function api(path, options) {
    let response;
    try {
        response = await fetch(API_BASE + path, {
            headers: { "Content-Type": "application/json" },
            ...options,
        });
    } catch (networkError) {
        showToast("Cannot reach the SocialConnect API. Is the backend running?", true);
        throw networkError;
    }

    if (response.status === 204) {
        return null;
    }

    const body = await response.json().catch(() => null);

    if (!response.ok) {
        const message = (body && body.message) || `Request failed (HTTP ${response.status})`;
        showToast(message, true);
        throw new Error(message);
    }

    return body;
}

const themeToggle = document.getElementById("theme-toggle");
function applyTheme(theme) {
    document.documentElement.setAttribute("data-theme", theme);
    themeToggle.innerHTML = theme === "light" ? "&#9789;" : "&#9788;";
    try {
        localStorage.setItem("socialconnect-theme", theme);
    } catch (e) {
    }
}
let savedTheme = "dark";
try {
    savedTheme = localStorage.getItem("socialconnect-theme") || "dark";
} catch (e) {
}
applyTheme(savedTheme);
themeToggle.addEventListener("click", () => {
    const current = document.documentElement.getAttribute("data-theme") === "light" ? "dark" : "light";
    applyTheme(current);
});

function saveSession(user) {
    currentUser = user;
    try {
        localStorage.setItem(SESSION_KEY, JSON.stringify(user));
    } catch (e) {
    }
}

function readSession() {
    try {
        const raw = localStorage.getItem(SESSION_KEY);
        return raw ? JSON.parse(raw) : null;
    } catch (e) {
        return null;
    }
}

function clearSession() {
    currentUser = null;
    try {
        localStorage.removeItem(SESSION_KEY);
    } catch (e) {
    }
}

const authScreen = document.getElementById("auth-screen");
const appScreen = document.getElementById("app-screen");
const tabLogin = document.getElementById("tab-login");
const tabSignup = document.getElementById("tab-signup");
const loginForm = document.getElementById("login-form");
const signupForm = document.getElementById("signup-form");

tabLogin.addEventListener("click", () => {
    tabLogin.classList.add("active");
    tabSignup.classList.remove("active");
    loginForm.hidden = false;
    signupForm.hidden = true;
});
tabSignup.addEventListener("click", () => {
    tabSignup.classList.add("active");
    tabLogin.classList.remove("active");
    signupForm.hidden = false;
    loginForm.hidden = true;
});

loginForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const username = document.getElementById("login-username").value;
    const password = document.getElementById("login-password").value;
    try {
        const user = await api("/auth/login", { method: "POST", body: JSON.stringify({ username, password }) });
        saveSession(user);
        showToast(`Welcome back, ${user.username}!`);
        showApp();
    } catch (err) {
    }
});

signupForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const payload = {
        username: document.getElementById("signup-username").value,
        email: document.getElementById("signup-email").value,
        password: document.getElementById("signup-password").value,
        fullName: document.getElementById("signup-fullname").value,
        country: document.getElementById("signup-country").value,
    };
    try {
        const user = await api("/users", { method: "POST", body: JSON.stringify(payload) });
        saveSession(user);
        showToast(`Welcome to SocialConnect, ${user.username}!`);
        showApp();
    } catch (err) {
    }
});

document.getElementById("logout-btn").addEventListener("click", () => {
    clearSession();
    location.reload();
});

async function showApp() {
    const stillExists = await fetch(`${API_BASE}/users/${currentUser.userId}`).then((r) => r.ok).catch(() => false);
    if (!stillExists) {
        clearSession();
        showToast("Your session is no longer valid (the server may have restarted) - please log in again.", true);
        return;
    }

    authScreen.hidden = true;
    appScreen.hidden = false;
    document.getElementById("current-user-chip").textContent = `@${currentUser.username}`;
    await loadGlobalData();
    populatePostTopicSelect();
    activateSection("home");
    await loadFeed();
}

const navLinks = document.querySelectorAll("nav .links a[data-section]");
const sections = document.querySelectorAll("main section");
const sectionLoaders = {
    "my-topics": loadMyTopicsPage,
    "reports": loadReports,
    "activity": loadActivity,
};

function activateSection(name) {
    navLinks.forEach((l) => l.classList.toggle("active", l.dataset.section === name));
    sections.forEach((s) => s.classList.toggle("active", s.id === name));
}

navLinks.forEach((link) => {
    link.addEventListener("click", () => {
        activateSection(link.dataset.section);
        const loader = sectionLoaders[link.dataset.section];
        if (loader) loader();
    });
});

function goToReportsFor(userId) {
    activateSection("reports");
    document.getElementById("r-reported").value = userId;
}

async function loadGlobalData() {
    const [users, topics] = await Promise.all([api("/users"), api("/topics")]);
    userMap = {};
    users.forEach((u) => { userMap[u.userId] = u.username; });
    topicsList = topics;
    topicsMap = {};
    topics.forEach((t) => { topicsMap[t.topicId] = t.topicName; });
}

function populatePostTopicSelect() {
    const select = document.getElementById("post-topic");
    const placeholder = `<option value="" disabled selected>Choose a topic...</option>`;
    const options = topicsList.map((t) => `<option value="${t.topicId}">${escapeHtml(capitalize(t.topicName))}</option>`).join("");
    select.innerHTML = placeholder + options;
}

const postContentInput = document.getElementById("post-content");
postContentInput.addEventListener("input", () => {
    document.getElementById("post-char-count").textContent = String(postContentInput.value.length);
});

const postTopicSelect = document.getElementById("post-topic");
const postTopicNewInput = document.getElementById("post-topic-new");
const newTopicToggle = document.getElementById("new-topic-toggle");
let newTopicMode = false;

newTopicToggle.addEventListener("click", () => {
    newTopicMode = !newTopicMode;
    newTopicToggle.classList.toggle("active", newTopicMode);
    newTopicToggle.textContent = newTopicMode ? "✕" : "+";
    newTopicToggle.title = newTopicMode ? "Pick from existing topics instead" : "Add a new topic";
    postTopicSelect.hidden = newTopicMode;
    postTopicSelect.disabled = newTopicMode;
    postTopicNewInput.hidden = !newTopicMode;
    postTopicNewInput.required = newTopicMode;
    postTopicSelect.required = !newTopicMode;
    if (newTopicMode) {
        postTopicNewInput.focus();
    } else {
        postTopicNewInput.value = "";
    }
});

async function resolveTopicId(newTopicName) {
    const normalized = newTopicName.trim().toLowerCase();
    const existing = topicsList.find((t) => t.topicName.toLowerCase() === normalized);
    if (existing) return existing.topicId;
    const created = await api("/topics", { method: "POST", body: JSON.stringify({ topicName: normalized }) });
    return created.topicId;
}

document.getElementById("post-form").addEventListener("submit", async (e) => {
    e.preventDefault();
    try {
        const topicId = newTopicMode
            ? await resolveTopicId(postTopicNewInput.value)
            : Number(postTopicSelect.value);

        await api("/posts", {
            method: "POST",
            body: JSON.stringify({ authorId: currentUser.userId, topicId, content: postContentInput.value }),
        });
        showToast(newTopicMode ? "Topic created and posted!" : "Posted!");
        e.target.reset();
        document.getElementById("post-char-count").textContent = "0";
        if (newTopicMode) {
            newTopicToggle.click();
        }
        await loadGlobalData();
        populatePostTopicSelect();
        await loadFeed();
    } catch (err) {
    }
});
document.getElementById("refresh-feed").addEventListener("click", loadFeed);

function formatDate(isoString) {
    if (!isoString) return "";
    return isoString.replace("T", " ").slice(0, 16);
}

async function loadFeed() {
    const list = document.getElementById("feed-list");
    list.innerHTML = "<p class=\"empty\">Loading...</p>";
    try {
        const posts = await api("/posts");
        if (!posts.length) {
            list.innerHTML = "<p class=\"empty\">No posts yet. Be the first to share something.</p>";
            return;
        }
        list.innerHTML = posts.map(renderPostCard).join("");
    } catch (e) {
        list.innerHTML = "<p class=\"empty\">Could not load the feed.</p>";
    }
}

function renderPostCard(post) {
    const isMine = post.authorId === currentUser.userId;
    return `
    <div class="item post-card" data-post-id="${post.postId}">
        <div class="item-title">
            <span><span class="post-author">${userLabel(post.authorId)}</span> &middot; <span class="topic-badge">${escapeHtml(topicsMap[post.topicId] || "topic")}</span></span>
        </div>
        <div class="item-meta">${formatDate(post.createdAt)}</div>
        <div class="post-content" data-role="content">${escapeHtml(post.content)}</div>
        <div class="post-actions">
            <button data-action="toggle-comments">Comments</button>
            ${isMine
                ? `<button data-action="edit-post">Edit</button>
                   <button class="danger" data-action="delete-post">Delete</button>`
                : `<button class="danger" data-action="report-user" data-user-id="${post.authorId}">Report</button>`}
        </div>
        <div class="comment-thread" data-role="comment-thread" hidden></div>
    </div>`;
}

const feedList = document.getElementById("feed-list");

feedList.addEventListener("click", async (e) => {
    const btn = e.target.closest("[data-action]");
    if (!btn) return;
    const action = btn.dataset.action;
    const postCard = btn.closest(".post-card");
    const postId = postCard ? postCard.dataset.postId : null;

    if (action === "toggle-comments") {
        const thread = postCard.querySelector("[data-role='comment-thread']");
        const isHidden = thread.hidden;
        thread.hidden = !isHidden;
        if (isHidden && !thread.dataset.loaded) {
            await loadCommentThread(postId, thread);
        }
        return;
    }

    if (action === "report-user") {
        goToReportsFor(btn.dataset.userId);
        return;
    }

    if (action === "delete-post") {
        if (!confirm("Delete this post?")) return;
        await api(`/posts/${postId}`, { method: "DELETE" });
        showToast("Post deleted");
        loadFeed();
        return;
    }

    if (action === "edit-post") {
        const contentEl = postCard.querySelector("[data-role='content']");
        const currentText = contentEl.textContent;
        contentEl.outerHTML = `
            <div class="post-content" data-role="content">
                <textarea data-role="edit-textarea" maxlength="200">${escapeHtml(currentText)}</textarea>
                <div class="post-actions" style="margin-top:8px;">
                    <button data-action="save-edit">Save</button>
                    <button data-action="cancel-edit">Cancel</button>
                </div>
            </div>`;
        return;
    }

    if (action === "cancel-edit") {
        loadFeed();
        return;
    }

    if (action === "save-edit") {
        const textarea = postCard.querySelector("[data-role='edit-textarea']");
        const newContent = textarea.value;
        const existing = await api(`/posts/${postId}`);
        await api(`/posts/${postId}`, {
            method: "PUT",
            body: JSON.stringify({ topicId: existing.topicId, content: newContent }),
        });
        showToast("Post updated");
        loadFeed();
        return;
    }

    if (action === "delete-comment") {
        if (!confirm("Delete this comment?")) return;
        await api(`/comments/${btn.dataset.commentId}`, { method: "DELETE" });
        const thread = postCard.querySelector("[data-role='comment-thread']");
        thread.dataset.loaded = "";
        await loadCommentThread(postId, thread);
        return;
    }
});

feedList.addEventListener("submit", async (e) => {
    const form = e.target.closest(".comment-form");
    if (!form) return;
    e.preventDefault();
    const postId = form.dataset.postId;
    const input = form.querySelector("input");
    const content = input.value.trim();
    if (!content) return;
    try {
        await api(`/posts/${postId}/comments`, {
            method: "POST",
            body: JSON.stringify({ authorId: currentUser.userId, content }),
        });
        input.value = "";
        const thread = document.querySelector(`[data-post-id="${postId}"] [data-role='comment-thread']`);
        thread.dataset.loaded = "";
        await loadCommentThread(postId, thread);
    } catch (err) {
    }
});

async function loadCommentThread(postId, threadEl) {
    threadEl.innerHTML = "<p class=\"empty\">Loading comments...</p>";
    try {
        const comments = await api(`/posts/${postId}/comments`);
        const commentsHtml = comments.length
            ? comments.map((c) => `
                <div class="comment-item">
                    <strong>${userLabel(c.authorId)}</strong>: ${escapeHtml(c.content)}
                    <span class="item-meta">${formatDate(c.createdAt)}</span>
                    ${c.authorId === currentUser.userId
                        ? `<button class="danger" data-action="delete-comment" data-comment-id="${c.commentId}" style="margin-left:8px;">Delete</button>`
                        : ""}
                </div>`).join("")
            : "<p class=\"empty\">No comments yet.</p>";
        threadEl.innerHTML = commentsHtml + `
            <form class="comment-form" data-post-id="${postId}">
                <input type="text" placeholder="Write a comment..." maxlength="300" required />
                <button type="submit">Reply</button>
            </form>`;
        threadEl.dataset.loaded = "1";
    } catch (e) {
        threadEl.innerHTML = "<p class=\"empty\">Could not load comments.</p>";
    }
}

document.getElementById("search-form").addEventListener("submit", async (e) => {
    e.preventDefault();
    const type = document.getElementById("search-type").value;
    const query = document.getElementById("search-query").value.trim();
    const results = document.getElementById("search-results");
    if (!query) return;
    results.innerHTML = "<p class=\"empty\">Searching...</p>";

    try {
        if (type === "people") {
            const [users, following] = await Promise.all([
                api(`/users?search=${encodeURIComponent(query)}`),
                api(`/follows/${currentUser.userId}/following`),
            ]);
            const followingIds = new Set(following.map((f) => f.followeeId));
            if (!users.length) {
                results.innerHTML = "<p class=\"empty\">No matching people found.</p>";
                return;
            }
            results.innerHTML = users.map((u) => `
                <div class="item">
                    <div class="item-title">
                        <span>${escapeHtml(u.username)} (#${u.userId})</span>
                        ${u.userId === currentUser.userId
                            ? "<span class=\"badge\">You</span>"
                            : `<button data-follow-user="${u.userId}" data-following="${followingIds.has(u.userId)}">
                                   ${followingIds.has(u.userId) ? "Unfollow" : "Follow"}
                               </button>`}
                    </div>
                    <div class="item-meta">${escapeHtml(u.fullName || "")} &middot; ${escapeHtml(u.country || "")}</div>
                </div>`).join("");
        } else {
            const [topics, followed] = await Promise.all([
                api(`/topics?search=${encodeURIComponent(query)}`),
                api(`/topic-follows/${currentUser.userId}`),
            ]);
            const followedSet = new Set(followed);
            if (!topics.length) {
                results.innerHTML = "<p class=\"empty\">No matching topics found.</p>";
                return;
            }
            results.innerHTML = topics.map((t) => `
                <div class="item">
                    <div class="item-title">
                        <span>${escapeHtml(capitalize(t.topicName))}</span>
                        <button data-follow-topic="${t.topicId}" data-following="${followedSet.has(t.topicId)}">
                            ${followedSet.has(t.topicId) ? "Unfollow" : "Follow"}
                        </button>
                    </div>
                </div>`).join("");
        }
    } catch (err) {
        results.innerHTML = "<p class=\"empty\">Search failed.</p>";
    }
});

document.getElementById("search-results").addEventListener("click", async (e) => {
    const userBtn = e.target.closest("[data-follow-user]");
    if (userBtn) {
        const followeeId = Number(userBtn.dataset.followUser);
        const isFollowing = userBtn.dataset.following === "true";
        try {
            if (isFollowing) {
                await api(`/follows?followerId=${currentUser.userId}&followeeId=${followeeId}`, { method: "DELETE" });
                showToast("Unfollowed");
            } else {
                await api("/follows", { method: "POST", body: JSON.stringify({ followerId: currentUser.userId, followeeId }) });
                showToast("Now following");
            }
            document.getElementById("search-form").requestSubmit();
        } catch (err) {
        }
        return;
    }

    const topicBtn = e.target.closest("[data-follow-topic]");
    if (topicBtn) {
        const topicId = Number(topicBtn.dataset.followTopic);
        const isFollowing = topicBtn.dataset.following === "true";
        try {
            if (isFollowing) {
                await api(`/topic-follows?userId=${currentUser.userId}&topicId=${topicId}`, { method: "DELETE" });
                showToast("Unfollowed topic");
            } else {
                await api("/topic-follows", { method: "POST", body: JSON.stringify({ userId: currentUser.userId, topicId }) });
                showToast("Following topic");
            }
            document.getElementById("search-form").requestSubmit();
        } catch (err) {
        }
    }
});

async function loadMyTopicsPage() {
    renderProfileCard();

    const followedList = document.getElementById("followed-topics-list");
    const allList = document.getElementById("all-topics-list");
    followedList.innerHTML = "<p class=\"empty\">Loading...</p>";
    allList.innerHTML = "<p class=\"empty\">Loading...</p>";

    try {
        const [followedIds, myPosts] = await Promise.all([
            api(`/topic-follows/${currentUser.userId}`),
            api(`/posts?authorId=${currentUser.userId}`),
        ]);
        const followedSet = new Set(followedIds);
        const followedTopics = topicsList.filter((t) => followedSet.has(t.topicId));
        const otherTopics = topicsList.filter((t) => !followedSet.has(t.topicId));

        followedList.innerHTML = followedTopics.length
            ? followedTopics.map((t) => renderFollowedTopicCard(t, myPosts.filter((p) => p.topicId === t.topicId))).join("")
            : "<p class=\"empty\">You're not following any topics yet - follow one below or via Search.</p>";

        allList.innerHTML = otherTopics.length
            ? otherTopics.map((t) => `
                <div class="item">
                    <div class="item-title">
                        <span>${escapeHtml(capitalize(t.topicName))}</span>
                        <button data-action="follow-topic-quick" data-topic-id="${t.topicId}">Follow</button>
                    </div>
                </div>`).join("")
            : "<p class=\"empty\">You're following every topic already.</p>";
    } catch (e) {
        followedList.innerHTML = "<p class=\"empty\">Could not load your topics.</p>";
    }
}

function renderProfileCard() {
    document.getElementById("profile-card").innerHTML = `
        <div class="profile-row">
            <div>
                <div class="item-title"><span>${escapeHtml(currentUser.username)}</span></div>
                <div class="item-meta">${escapeHtml(currentUser.fullName || "")} &middot; ${escapeHtml(currentUser.email || "")} &middot; ${escapeHtml(currentUser.country || "")}</div>
            </div>
            <button class="danger" id="delete-account-btn">Delete my account</button>
        </div>`;
    document.getElementById("delete-account-btn").addEventListener("click", async () => {
        if (!confirm("Delete your account? This cannot be undone.")) return;
        try {
            await api(`/users/${currentUser.userId}`, { method: "DELETE" });
            showToast("Account deleted");
            clearSession();
            location.reload();
        } catch (err) {
        }
    });
}

function renderFollowedTopicCard(topic, myPostsInTopic) {
    const postsHtml = myPostsInTopic.length
        ? myPostsInTopic.map((p) => `
            <div class="comment-item">
                ${escapeHtml(p.content)}
                <button class="danger" data-action="delete-my-post" data-post-id="${p.postId}" style="margin-left:8px;">Delete</button>
            </div>`).join("")
        : "<p class=\"empty\">You haven't posted in this topic yet.</p>";

    return `
    <div class="card topic-follow-card" data-topic-id="${topic.topicId}">
        <div class="item-title">
            <span>${escapeHtml(capitalize(topic.topicName))}</span>
            <button class="danger" data-action="unfollow-topic" data-topic-id="${topic.topicId}">Unfollow</button>
        </div>
        <div style="margin-top:10px;">${postsHtml}</div>
        <form class="comment-form" data-action="quick-post" data-topic-id="${topic.topicId}" style="margin-top:12px;">
            <input type="text" placeholder="Post something about ${escapeHtml(capitalize(topic.topicName))}..." maxlength="200" required />
            <button type="submit">Post</button>
        </form>
    </div>`;
}

document.getElementById("followed-topics-list").addEventListener("click", async (e) => {
    const unfollowBtn = e.target.closest("[data-action='unfollow-topic']");
    if (unfollowBtn) {
        await api(`/topic-follows?userId=${currentUser.userId}&topicId=${unfollowBtn.dataset.topicId}`, { method: "DELETE" });
        showToast("Unfollowed topic");
        loadMyTopicsPage();
        return;
    }
    const deleteBtn = e.target.closest("[data-action='delete-my-post']");
    if (deleteBtn) {
        if (!confirm("Delete this post?")) return;
        await api(`/posts/${deleteBtn.dataset.postId}`, { method: "DELETE" });
        showToast("Post deleted");
        loadMyTopicsPage();
    }
});

document.getElementById("followed-topics-list").addEventListener("submit", async (e) => {
    const form = e.target.closest("[data-action='quick-post']");
    if (!form) return;
    e.preventDefault();
    const input = form.querySelector("input");
    try {
        await api("/posts", {
            method: "POST",
            body: JSON.stringify({ authorId: currentUser.userId, topicId: Number(form.dataset.topicId), content: input.value }),
        });
        showToast("Posted!");
        loadMyTopicsPage();
    } catch (err) {
    }
});

document.getElementById("all-topics-list").addEventListener("click", async (e) => {
    const btn = e.target.closest("[data-action='follow-topic-quick']");
    if (!btn) return;
    await api("/topic-follows", { method: "POST", body: JSON.stringify({ userId: currentUser.userId, topicId: Number(btn.dataset.topicId) }) });
    showToast("Following topic");
    loadMyTopicsPage();
});

document.getElementById("report-form").addEventListener("submit", async (e) => {
    e.preventDefault();
    const payload = {
        reporterId: currentUser.userId,
        reportedUserId: Number(document.getElementById("r-reported").value),
        reason: document.getElementById("r-reason").value,
    };
    try {
        await api("/reports", { method: "POST", body: JSON.stringify(payload) });
        showToast("Report submitted");
        e.target.reset();
        loadReports();
    } catch (err) {
    }
});
document.getElementById("refresh-reports").addEventListener("click", loadReports);

async function loadReports() {
    const list = document.getElementById("reports-list");
    list.innerHTML = "<p class=\"empty\">Loading...</p>";
    try {
        const reports = await api("/reports");
        if (!reports.length) {
            list.innerHTML = "<p class=\"empty\">No reports yet.</p>";
            return;
        }
        list.innerHTML = reports.map((r) => {
            const isMine = r.reporterId === currentUser.userId;
            return `
            <div class="item">
                <div class="item-title">
                    <span>${userLabel(r.reporterId)} reported ${userLabel(r.reportedUserId)}</span>
                    <span class="badge ${r.status.toLowerCase()}">${r.status}</span>
                </div>
                <div class="item-meta">${escapeHtml(r.reason)}</div>
                ${isMine ? `
                <div class="row" style="margin-top:10px;">
                    <select data-status-select="${r.reportId}">
                        <option value="PENDING" ${r.status === "PENDING" ? "selected" : ""}>Pending</option>
                        <option value="REVIEWED" ${r.status === "REVIEWED" ? "selected" : ""}>Reviewed</option>
                        <option value="DISMISSED" ${r.status === "DISMISSED" ? "selected" : ""}>Dismissed</option>
                    </select>
                    <button data-update-status="${r.reportId}">Update status</button>
                    <button class="danger" data-delete-report="${r.reportId}">Delete</button>
                </div>` : `<p class="item-meta">Only the reporter can manage this report.</p>`}
            </div>`;
        }).join("");

        list.querySelectorAll("[data-update-status]").forEach((btn) => {
            btn.addEventListener("click", async () => {
                const id = btn.dataset.updateStatus;
                const status = list.querySelector(`[data-status-select="${id}"]`).value;
                await api(`/reports/${id}/status`, { method: "PUT", body: JSON.stringify({ status }) });
                showToast("Report status updated");
                loadReports();
            });
        });
        list.querySelectorAll("[data-delete-report]").forEach((btn) => {
            btn.addEventListener("click", async () => {
                if (!confirm("Delete this report?")) return;
                await api(`/reports/${btn.dataset.deleteReport}`, { method: "DELETE" });
                showToast("Report deleted");
                loadReports();
            });
        });
    } catch (e) {
        list.innerHTML = "<p class=\"empty\">Could not load reports.</p>";
    }
}

const ACTIVITY_LABELS = {
    FOLLOW: "Started following someone",
    UNFOLLOW: "Unfollowed someone",
    REPORT_SUBMITTED: "Submitted a report",
    POST_CREATED: "Shared a new post",
    COMMENT_ADDED: "Added a comment",
    ACCOUNT_SEEDED: "Account created (sample data)",
};
function formatActivityType(type) {
    if (ACTIVITY_LABELS[type]) return ACTIVITY_LABELS[type];
    if (type && type.startsWith("REPORT_STATUS_")) {
        const status = type.replace("REPORT_STATUS_", "");
        return `Report marked as ${status.charAt(0) + status.slice(1).toLowerCase()}`;
    }
    return type;
}

document.getElementById("refresh-activity").addEventListener("click", loadActivity);

async function loadActivity() {
    const list = document.getElementById("activity-list");
    list.innerHTML = "<p class=\"empty\">Loading...</p>";
    try {
        const logs = await api("/activity-log");
        if (!logs.length) {
            list.innerHTML = "<p class=\"empty\">No activity yet.</p>";
            return;
        }
        list.innerHTML = logs
            .slice()
            .reverse()
            .map((l) => `
            <div class="item">
                <div class="item-title"><span>${escapeHtml(formatActivityType(l.activityType))}</span></div>
                <div class="item-meta">${userLabel(l.userId)} &middot; ${formatDate(l.activityDate)}</div>
            </div>`)
            .join("");
    } catch (e) {
        list.innerHTML = "<p class=\"empty\">Could not load activity log.</p>";
    }
}

(function init() {
    const session = readSession();
    if (session) {
        currentUser = session;
        showApp();
    }
})();
