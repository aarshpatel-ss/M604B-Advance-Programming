INSERT INTO Users (user_id, username, email, password_hash, full_name, country, created_at) VALUES
(1,  'emilys',      'emily.johnson@x.dummyjson.com',    'Password123!', 'Emily Johnson',      'United States', '2025-01-05'),
(2,  'michaelw',    'michael.williams@x.dummyjson.com', 'Password123!', 'Michael Williams',   'United States', '2025-01-06'),
(3,  'sophiab',     'sophia.brown@x.dummyjson.com',     'Password123!', 'Sophia Brown',       'United States', '2025-01-07'),
(4,  'jamesd',      'james.davis@x.dummyjson.com',      'Password123!', 'James Davis',        'United States', '2025-01-08'),
(5,  'emmaj',       'emma.miller@x.dummyjson.com',      'Password123!', 'Emma Miller',        'United States', '2025-01-09'),
(6,  'oliviaw',     'olivia.wilson@x.dummyjson.com',    'Password123!', 'Olivia Wilson',      'United States', '2025-01-10'),
(7,  'alexanderj',  'alexander.jones@x.dummyjson.com',  'Password123!', 'Alexander Jones',    'United States', '2025-01-11'),
(8,  'avat',        'ava.taylor@x.dummyjson.com',       'Password123!', 'Ava Taylor',         'United States', '2025-01-12'),
(9,  'ethanm',      'ethan.martinez@x.dummyjson.com',   'Password123!', 'Ethan Martinez',     'United States', '2025-01-13'),
(10, 'isabellad',   'isabella.anderson@x.dummyjson.com','Password123!', 'Isabella Anderson',  'United States', '2025-01-14'),
(11, 'liamg',       'liam.garcia@x.dummyjson.com',      'Password123!', 'Liam Garcia',        'United States', '2025-01-15'),
(12, 'miar',        'mia.rodriguez@x.dummyjson.com',    'Password123!', 'Mia Rodriguez',      'United States', '2025-01-16'),
(13, 'noahh',       'noah.hernandez@x.dummyjson.com',   'Password123!', 'Noah Hernandez',     'United States', '2025-01-17'),
(14, 'charlottem',  'charlotte.lopez@x.dummyjson.com',  'Password123!', 'Charlotte Lopez',    'United States', '2025-01-18'),
(15, 'williamg',    'william.gonzalez@x.dummyjson.com', 'Password123!', 'William Gonzalez',   'United States', '2025-01-19'),
(16, 'averyp',      'avery.perez@x.dummyjson.com',      'Password123!', 'Avery Perez',        'United States', '2025-01-20'),
(17, 'evelyns',     'evelyn.sanchez@x.dummyjson.com',   'Password123!', 'Evelyn Sanchez',     'United States', '2025-01-21'),
(18, 'logant',      'logan.torres@x.dummyjson.com',     'Password123!', 'Logan Torres',       'United States', '2025-01-22'),
(19, 'abigailr',    'abigail.rivera@x.dummyjson.com',   'Password123!', 'Abigail Rivera',     'United States', '2025-01-23'),
(20, 'jacksone',    'jackson.evans@x.dummyjson.com',    'Password123!', 'Jackson Evans',      'United States', '2025-01-24');

INSERT INTO Topics (topic_id, topic_name) VALUES
(1, 'greeting'),
(2, 'mood'),
(3, 'question'),
(4, 'lifestyle'),
(5, 'suggestion'),
(6, 'politics'),
(7, 'technology'),
(8, 'travel');

INSERT INTO Posts (post_id, author_id, topic_id, content, created_at) VALUES
(1,  1,  1, 'Good morning everyone, hope your week is off to a great start!', '2025-04-01 08:00:00'),
(2,  2,  2, 'Feeling surprisingly motivated this morning, ready to get things done.', '2025-04-01 08:15:00'),
(3,  3,  3, 'Does anyone else struggle to stay focused during long work calls?', '2025-04-01 09:00:00'),
(4,  4,  4, 'Switched to a 5am routine this week and honestly it has been great so far.', '2025-04-01 09:30:00'),
(5,  5,  5, 'We should really push for more remote-friendly policies at work.', '2025-04-01 10:00:00'),
(6,  6,  6, 'Local elections are coming up soon, has anyone been reading the manifestos?', '2025-04-01 10:30:00'),
(7,  7,  7, 'Finally tried the new AI coding assistant everyone is talking about, pretty impressive.', '2025-04-01 11:00:00'),
(8,  8,  8, 'Planning a short trip next month, any budget-friendly destination ideas?', '2025-04-01 11:30:00'),
(9,  9,  2, 'Rough start to the day but coffee is helping. Anyone else feeling it today?', '2025-04-01 12:00:00'),
(10, 10, 3, 'Is it just me or is everyone burnt out lately? Genuinely curious how others cope.', '2025-04-01 12:30:00'),
(11, 11, 4, 'Trying to cut back on screen time in the evenings, has anyone actually managed this?', '2025-04-01 13:00:00'),
(12, 12, 5, 'Small suggestion: a 15-minute buffer between meetings would change everyone''s day.', '2025-04-01 13:30:00'),
(13, 13, 6, 'Interesting to see how different countries are handling the same policy issue right now.', '2025-04-01 14:00:00'),
(14, 14, 1, 'Happy Monday to whoever needed to hear it today.', '2025-04-01 14:30:00'),
(15, 15, 7, 'Debugging a race condition for three hours just to find a missing synchronized block.', '2025-04-01 15:00:00'),
(16, 16, 8, 'Just booked flights on a whim, first solo trip in years.', '2025-04-01 15:30:00'),
(17, 17, 2, 'Oddly calm today, which is rare and I am enjoying it while it lasts.', '2025-04-01 16:00:00'),
(18, 18, 3, 'Honest question: does anyone actually enjoy Mondays or do we all just pretend?', '2025-04-01 16:30:00'),
(19, 19, 5, 'Suggestion for the team: async updates over yet another status meeting.', '2025-04-01 17:00:00'),
(20, 20, 4, 'Started meal-prepping on Sundays, wish I had done this years ago.', '2025-04-01 17:30:00');

INSERT INTO Comments (comment_id, post_id, author_id, content, created_at) VALUES
(1,  1,  4,  'Good morning! Same to you.', '2025-04-01 08:05:00'),
(2,  1,  9,  'Needed this today, thanks.', '2025-04-01 08:10:00'),
(3,  3,  10, 'Every single call, honestly.', '2025-04-01 09:10:00'),
(4,  3,  2,  'Try muting notifications, helped me a bit.', '2025-04-01 09:20:00'),
(5,  4,  6,  'How are you finding the early mornings so far?', '2025-04-01 09:40:00'),
(6,  5,  1,  'Fully agree, would make a huge difference.', '2025-04-01 10:10:00'),
(7,  6,  13, 'I have been following it a bit too, hard to keep up.', '2025-04-01 10:40:00'),
(8,  7,  15, 'Which one did you try? Curious to compare.', '2025-04-01 11:10:00'),
(9,  8,  16, 'Depends on your budget, but coastal towns are usually cheaper off-season.', '2025-04-01 11:40:00'),
(10, 9,  17, 'Coffee is doing the heavy lifting for me too.', '2025-04-01 12:10:00'),
(11, 10, 18, 'Taking short walks between tasks has helped me a lot.', '2025-04-01 12:40:00'),
(12, 12, 5,  'This is such a simple fix honestly, surprised it is not standard.', '2025-04-01 13:40:00'),
(13, 15, 7,  'Concurrency bugs are the worst to track down.', '2025-04-01 15:10:00'),
(14, 18, 14, 'I mostly just pretend at this point.', '2025-04-01 16:40:00'),
(15, 20, 11, 'Meal-prepping was a game changer for me too.', '2025-04-01 17:40:00');

INSERT INTO Topic_Follows (user_id, topic_id, followed_at) VALUES
(1, 2, '2025-04-02'),
(1, 4, '2025-04-02'),
(2, 1, '2025-04-02'),
(2, 5, '2025-04-02'),
(3, 3, '2025-04-02'),
(3, 6, '2025-04-02'),
(4, 2, '2025-04-02'),
(4, 7, '2025-04-02'),
(5, 4, '2025-04-02'),
(5, 8, '2025-04-02'),
(6, 1, '2025-04-02'),
(6, 5, '2025-04-02'),
(7, 3, '2025-04-02'),
(8, 6, '2025-04-02'),
(9, 2, '2025-04-02');

INSERT INTO Follows (follower_id, followee_id, followed_at) VALUES
(1, 2, '2025-02-01'),
(1, 3, '2025-02-01'),
(2, 1, '2025-02-02'),
(2, 4, '2025-02-02'),
(3, 5, '2025-02-03'),
(4, 1, '2025-02-03'),
(5, 6, '2025-02-04'),
(6, 7, '2025-02-04'),
(7, 8, '2025-02-05'),
(8, 9, '2025-02-05');

INSERT INTO Reports (report_id, reporter_id, reported_user_id, reason, status, created_at) VALUES
(1, 3,  17, 'Repeated spam-like posting',          'PENDING',   '2025-03-01'),
(2, 6,  9,  'Offensive language in comments',       'REVIEWED',  '2025-03-02'),
(3, 8,  20, 'Impersonating another user',           'PENDING',   '2025-03-03'),
(4, 2,  11, 'Harassment via comments',              'DISMISSED', '2025-03-04'),
(5, 14, 5,  'Posting misleading information',       'PENDING',   '2025-03-05'),
(6, 19, 7,  'Spam links in profile',                'REVIEWED',  '2025-03-06');

INSERT INTO User_Activity_Log (log_id, user_id, activity_type, activity_date) VALUES
(1, 1, 'ACCOUNT_SEEDED', '2025-01-05 09:00:00'),
(2, 2, 'ACCOUNT_SEEDED', '2025-01-06 09:00:00');

ALTER TABLE Users ALTER COLUMN user_id RESTART WITH 21;
ALTER TABLE Topics ALTER COLUMN topic_id RESTART WITH 9;
ALTER TABLE Posts ALTER COLUMN post_id RESTART WITH 21;
ALTER TABLE Comments ALTER COLUMN comment_id RESTART WITH 16;
ALTER TABLE Reports ALTER COLUMN report_id RESTART WITH 7;
ALTER TABLE User_Activity_Log ALTER COLUMN log_id RESTART WITH 3;
