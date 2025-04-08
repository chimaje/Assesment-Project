import React from "react";
import { Header } from "./Header.jsx";
import { Outlet } from "react-router-dom";

export function Layout() {
    return (
        <div className="min-h-screen">
            <Header />
            <div className="relative flex justify-start p-4">
                <Outlet />
            </div>
        </div>
    );
}
