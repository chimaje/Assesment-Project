
import { createBrowserRouter, RouterProvider ,Navigate} from "react-router-dom";
import './App.css'
import {Layout} from "./constants/Layout.jsx";
import {Portal} from "./pages/Portal.jsx";
import {AllCourses} from "./pages/Courses/View_Course.jsx";
import {UserRegistration} from "./pages/Log_In/Register.jsx";
import {Login} from "./pages/Log_In/Login.jsx";
import { My_Courses } from './pages/Courses/User_Courses.jsx';
import { Books } from './pages/Books/Books_main_page.jsx';
import { Profile } from "./pages/Profile.jsx";
import { EligibilityCheck } from "./pages/Eligibilty/Eligibility_check.jsx";

function App() {
    const router = createBrowserRouter([
        {  path: '/', element: <Navigate to="/log_in" replace /> },
        { path:'/register',element:<UserRegistration/>},
        { path:'/log_in',element:<Login/>},

        {
            path: '/home',
            element: <Layout />,
            children: [
                { path:'/home', element:<Portal/>  },

                {path:'/home/profile',element:<Profile/>},

                {path:'/home/eligibility_check',element:<EligibilityCheck/>},

                {path:'/home/courses',element:<AllCourses/>},

                {path:'/home/courses/my_courses',element:<My_Courses/>},
                
                {path:'/home/books',element:<Books/>},
            ],
        },

    ]);

  return (
      <RouterProvider router={router} />
  )
}

export default App
