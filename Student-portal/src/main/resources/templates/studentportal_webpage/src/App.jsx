import { useState } from 'react'
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import './App.css'
import {Layout} from "./constants/Layout.jsx";
import {Portal} from "./pages/Portal.jsx";
import {AllCourses} from "./pages/Courses/View_Course.jsx";

function App() {
    const router = createBrowserRouter([
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
