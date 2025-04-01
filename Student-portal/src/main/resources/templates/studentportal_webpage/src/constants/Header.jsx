import React from "react";
import {NavBar} from "./navbar.jsx";


export function Header(){
    return(
        <div className="flex flex-row items-baseline justify-between sticky top-0 py-2  bg-gray-200 gap-[40px]">
            <p className="text-4xl font-bold text-black pl-3">
                <a  href="/">Student Portal</a>
            </p>

            <NavBar/>
        </div>
    )
}
