import React from "react";
import { userService } from "../services/api";

export function NavBar(){
    return(
        <nav className="flex items-center gap-8">
            <div className="hidden md:flex items-center gap-6" >
                <a 
                    href='/home/courses' 
                    className="!text-gray-600 hover:!text-blue-600 transition-colors font-medium text-sm relative after:absolute after:bottom-0 after:left-0 after:w-0 after:h-[2px] after:bg-blue-600 hover:after:w-full after:transition-all"
                >
                    View Courses
                </a>
                <a 
                    href="/home/books" 
                    className="!text-gray-600 hover:!text-blue-600 transition-colors font-medium text-sm relative after:absolute after:bottom-0 after:left-0 after:w-0 after:h-[2px] after:bg-blue-600 hover:after:w-full after:transition-all"
                >
                    Books Borrowed
                </a>
                <a 
                    href='/home/courses/my_courses' 
                    className="!text-gray-600 hover:!text-blue-600 transition-colors font-medium text-sm relative after:absolute after:bottom-0 after:left-0 after:w-0 after:h-[2px] after:bg-blue-600 hover:after:w-full after:transition-all"
                >
                    My Courses
                </a>
                <a 
                    href='/home/invoices' 
                    className="!text-gray-600 hover:!text-blue-600 transition-colors font-medium text-sm relative after:absolute after:bottom-0 after:left-0 after:w-0 after:h-[2px] after:bg-blue-600 hover:after:w-full after:transition-all"
                >
                    Invoices
                </a>
            </div>
            
            <div className="flex items-center gap-4 ml-4">
                <div className="hidden md:flex items-center gap-4 border-l border-gray-200 pl-4">
                    <a 
                        href='/home/profile' 
                        className="flex items-center gap-2 !text-gray-600 hover:!text-blue-600 transition-colors font-medium text-sm"
                    >
                        <svg 
                            className="w-5 h-5" 
                            fill="none" 
                            stroke="currentColor" 
                            viewBox="0 0 24 24"
                        >
                            <path 
                                strokeLinecap="round" 
                                strokeLinejoin="round" 
                                strokeWidth={2} 
                                d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" 
                            />
                        </svg>
                        Profile
                    </a>
                    <a 
                        onChange={()=> userService.logout}
                        href='/log_in' 
                        className="px-3 py-1.5 bg-red-50 !text-red-600 hover:!bg-red-100 rounded-md font-medium text-sm transition-colors"
                    >
                        Log Out
                    </a>
                </div>
            </div>
        </nav>
    );
}