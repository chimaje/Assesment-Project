/* eslint-disable no-unused-vars */
const STUDENT_URL = 'http://localhost:8083/students'
const COURSE_URL ='http://localhost:8083/courses'
const FINANCE_INVOICE_URL = 'http://localhost:8081/api/invoices';
// const FINANCE_ACCOUNT_URL = 'http://localhost:8081/accounts';
const LIBRARY_BASE_URL= 'http://localhost';
const USER_URL = 'http://localhost:8083/users';

const handleError = (error, serviceName) => {
    const errorMessage = error.response?.data?.message || 
                        error.response?.data?.error || 
                        error.message || 
                        `Unknown error in ${serviceName}`;
    throw new Error(errorMessage);
};
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
const Libraryapi = axios.create({
    baseURL: LIBRARY_BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    }
});
const Studentapi = axios.create({
    baseURL : STUDENT_URL,
    headers:{
        'Content-Type': 'application/json',
    }
});
const Financeapi = axios.create({
    baseURL:FINANCE_INVOICE_URL,
    headers:{
       'Content-Type': 'application/json', 
    }
});

export const financeService = {
    getInvoices:async (studentId) => {
        try {
            const response = await Financeapi.get(`/student/${studentId}`);
            return response.data._embedded.invoiceList;
          } catch (error) {
            throw new Error('Failed to fetch invoices');
          }
    },
    payInvoice: async (reference) => {
        try {
          await Financeapi.put(`/${reference}/pay`);
        } catch (error) {
          throw new Error('Payment failed');
        }
      },
    
      cancelInvoice: async (reference) => {
        try {
          await Financeapi.delete(`/${reference}/cancel`);
        } catch (error) {
          throw new Error('Cancellation failed');
        }
      }
}

export const studentService = {
    //get student info 
    getStudentByUser: async () => {
        try {
            const username = userService.getCurrentUser();
            const response = await Studentapi.get(`/user/${username}`);
            return response.data;
        } catch (error) {
            if (error.response?.status === 404) return null;
            handleError(error, 'studentService');
        }
    },

    //update student info
    updateStudentInfo: async (externalStudentId, updateData) => {
        try {
            const response = await Studentapi.put(`/${externalStudentId}`, updateData);
            return response.data;
        } catch (error) {
            handleError(error, 'studentService');
        }
    }

};

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
};
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

export const LibraryService = {
    getBorrowedBooks: async () => {
        try {
          const response = await Libraryapi.get('/api/borrowedbooks');
          return response.data;
        } catch (error) {
          if (error.response) {
            // Server responded with non-2xx status
            console.error('API Error:', error.response.status, error.response.data);
            throw new Error(`Library service error: ${error.response.status}`);
          } else {
            console.error('Network Error:', error.message);
            throw new Error('Cannot connect to library service');
          }
        }
      }
  };

