import { useState } from 'react'
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import './App.css'
import {Layout} from "./constants/Layout.jsx";
import {Portal} from "./pages/Portal.jsx";
import {AllCourses} from "./pages/Courses/View_Course.jsx";
import {UserRegistration} from "./pages/Log_In/Register.jsx";
import {Login} from "./pages/Log_In/Login.jsx";

function App() {
    const router = createBrowserRouter([
        { path:'/register',element:<UserRegistration/>},
        { path:'/log_in',element:<Login/>},
        {
            path: '/',
            element: <Layout />,
            children: [
                { path:'/', element:<Portal/>  },

                {
                    path:'/courses',
                    element:<AllCourses/>,
                    children:[

                    ]
                }
            ],
        },

    ]);

  return (
      <RouterProvider router={router} />
  )
}

export default App
