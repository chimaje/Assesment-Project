const STUDENT_URL = 'http://localhost:8083/students'
const COURSE_URL ='http://localhost:8083/courses'
const FINANCE_INVOICE_URL = 'http://localhost:8081/api/invoices';
const FINANCE_ACCOUNT_URL = 'http://localhost:8081/accounts';
const LIBRARY_BASE_URL= ''
const USER_URL = 'http://localhost:8083/users';

import axios from "axios";

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
         enrollInCourse: async (courseId, userId) => {
             try {
                 const response = await apiClient.post(`/courses/${userId}/${courseId}/enroll`);
                 return response.data;
             } catch (error) {
                 throw new Error(`Enrollment failed: ${error.message}`);
             }
         },
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
                Username:loginData.username,
                password:loginData.password
            });
            return response.data;
        }catch(error){
            throw new Error(`Log In failed : ${error.response?.data?.message || error.message}`)
        }
    },

};

//     // Get single course
//     getCourseById: async (courseId) => {
//         try {
//             const response = await apiClient.get(`/courses/${courseId}`);
//             return response.data;
//         } catch (error) {
//             throw new Error(`Failed to fetch course: ${error.message}`);
//         }
//     },
//
//     // Enroll in course
//     enrollInCourse: async (courseId, studentId) => {
//         try {
//             const response = await apiClient.post(`/courses/${courseId}/enroll/${studentId}`);
//             return response.data;
//         } catch (error) {
//             throw new Error(`Enrollment failed: ${error.message}`);
//         }
//     }
// };
//
// // Add other API services as needed
// export const invoiceService = {
//     // Invoice-related endpoints
// };