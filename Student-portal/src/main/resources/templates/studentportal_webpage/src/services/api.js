/* eslint-disable no-unused-vars */
const STUDENT_URL = 'http://localhost:8083/students'
const COURSE_URL ='http://localhost:8083/courses'
const FINANCE_INVOICE_URL = 'http://localhost:8081/api/invoices';
const FINANCE_ACCOUNT_URL = 'http://localhost:8081/accounts';
const LIBRARY_BASE_URL= ''
const USER_URL = 'http://localhost:8083/users';

import axios from "axios";

let currentUser = null;
// Create axios instance with base config
const Courseapi = axios.create({
    baseURL: COURSE_URL,
    headers: {
        'Content-Type': 'application/json',

    }
});
const Userapi = axios.create({
    baseURL: USER_URL,
    headers: {
        'Content-Type': 'application/json',
    }
});

export const courseService = {
    // Get all courses
    getAllCourses: async () => {
        try {
            const response = await Courseapi.get('');
            if (Array.isArray(response.data)) {
                return response.data;
            }

            throw new Error("Unexpected response format");
        } catch (error) {
            throw new Error(`Failed to fetch courses: ${error.message}`);
        }
    },
     //Enroll in course
         enrollInCourse: async (courseId,username) => {
         console.log("request pending to be sents")
             try {
             console.log("request pending to be sents")
             if (!username ) throw new Error('User not logged in')
                 const response = await Courseapi.post(`/${username}/${courseId}/enroll`);
                 console.log(response);
                 return response.data;
             } catch (error) {
                 throw new Error(`Enrollment failed: ${error.message}`);
             }
         },
    // View Users Enrolled Courses
    getEnrolledCourses: async (username)=>{
        try{
            const response = await Userapi.get(`/${username}/my_courses`)
            return response.data;
        }catch(error){
            throw new Error(`Failed to fetch enrolled courses : ${error.message}`)
        }
    }
}
export const userService = {
    // Register new user
    registerUser: async (userData) => {
        try {
            const response = await Userapi.post('/create', {
                username: userData.username,
                email: userData.email,
                surname: userData.surname,
                forename: userData.forename,
                password: userData.password
            });
            return response.data;
        } catch (error) {
            const errorMsg = error.response?.data?.message ||
                                  error.response?.data?.error ||
                                  'Registration failed';
                  throw new Error(errorMsg);
        }
    },
    //Log In a User
    login: async (loginData) =>{
        try{
            const response = await Userapi.post('/login',{
                username:loginData.username,
                password:loginData.password
            });
              localStorage.setItem('currentUser', loginData.username);
              currentUser = loginData.username;

            return response.data;
        }catch(error){
            throw new Error(`Log In failed : ${error.response?.data?.message || error.message}`)
        }
    },
   getCurrentUser: () => {
            const currentUser = localStorage.getItem('currentUser');

           return currentUser;
       },

   logout: () => {
           localStorage.removeItem('currentUser');
           currentUser = null;
       }

};

