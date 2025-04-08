import React from "react";
import { NavBar } from "./NavBar.jsx";

export function Header() {
    return (
        <div className="flex flex-col md:flex-row items-center md:items-baseline justify-between w-full sticky top-0 py-2 bg-gray-200 px-4">
            <p className="text-4xl font-bold text-black">
                <a href="/">Student Portal</a>
            </p>
            <NavBar />
        </div>
    );
}
