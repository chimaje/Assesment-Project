import React from "react"
import {Header} from "./Header.jsx";
import { Outlet } from "react-router-dom";


export function Layout(){
    return(
        <div className="min-h-screen ">
            <div className=" relative flex justify-start ">
                <Header/>
            </div>
            <div className=" relative flex justify-start ">
                <Outlet />
            </div>
        </div>
    )
}
