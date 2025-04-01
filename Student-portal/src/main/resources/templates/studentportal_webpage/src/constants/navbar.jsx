import React from "react";

export function NavBar(){
    return(
        <div className="flex flex-row">

                <div className="flex flex-row mt-0 mb-[10px] mr-[10px] ml-[20px] gap-4">
                    <div>
                        <a href='/courses'>View Courses</a>
                    </div>
                    <div>
                        <a>Books Borrowed</a>
                    </div>
                    <div>
                        <a>My Courses</a>
                    </div>
                    <div>
                        <a>Invoices</a>
                    </div>
                </div>
                <div className="flex flex-row mt-0 mb-[10px] mr-[10px] ml-[189px] gap-4">
                    <div>
                        <a>Log Out</a>
                    </div>
                    <div>
                        <a>Profile</a>
                    </div>
                </div>
        </div>
    )
}