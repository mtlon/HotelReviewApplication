INSERT INTO users (firstname, lastname, password, role_name, username) VALUES
('admin', '', '$2a$12$CGq44GkM3nNTZCgbqnOxDOMihKvdTWYAzw101mU.tK5iTpkRZLtRS', 'ROLE_ADMIN', 'admin'),
('admin', '', '$2a$12$mNhKJT54y.jJSXPckq.3FeXUolrBhTN.Ew7YJYT2.6V.L0.bcdNg.', 'ROLE_MODERATOR', 'moderator');

INSERT INTO hotel (city, name, number_of_rooms) VALUES
('Las Vegas Cesar Palace', 'Cesar Palace', 2000),
('Tokyo Mandarin Oriental', 'Mandarin Oriental', 157),
('Sydney Four Seasons Hotel', 'Four Seasons Hotel', 531),
('Paris Ritz-Carlton', 'Ritz-Carlton', 159);

INSERT INTO review (content, stars, title, hotel_id) VALUES
('Great hotel! Wonderful experience. I highly recommend it!', 5, 'Amazing stay', 1),
('The room was cozy and comfortable, and the view was breathtaking!', 5, 'Wonderful view', 2),
('This hotel was fantastic. The location was perfect and the room was very comfortable.', 5, 'Excellent hotel', 3),
('The room was clean and comfortable, but the hotel was a bit noisy.', 3, 'Noisy hotel', 4);
