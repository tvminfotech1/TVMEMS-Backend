

--> For User Table
INSERT INTO user (full_name,mobile_number,email,aadhar,dob,gender,password,status)
VALUES (
    'test',
    1234567890,
    'test@gmail.com',
    '123123123123',
    '2025-10-07T13:36:06.536',
    'male',
--    password="test@123"
    '$2a$10$dhpJO0y77TZNoWoXYpxlUe6GHIZrqatANZHILppaVxBXX/gURjknq',
    true
);

--> For User_Roles Table
INSERT INTO user_roles (user_employee_id, roles)
VALUES (1, 'ROLE_ADMIN');