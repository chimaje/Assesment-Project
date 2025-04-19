import React from "react";
import { userService } from '../services/api.js';
export function Portal() {
    const username =  userService.getCurrentUser();
    return (
        <div className="max-w-4xl mx-auto px-4 py-8 md:py-12">
            <div className="bg-white rounded-2xl shadow-lg p-6 md:p-8 transition-all hover:shadow-xl">
                <div className="flex items-center gap-4 mb-6">
                    <div className="bg-blue-100 p-3 rounded-full">
                        <span className="text-2xl">👋</span>
                    </div>
                    <h3 className="text-3xl font-bold text-gray-800">
                        Hello, {username}!
                    </h3>
                </div>

                <div className="space-y-5 text-gray-600">
                    <p className="text-lg leading-relaxed font-medium text-blue-600">
                        Welcome to your student portal!
                    </p>

                    <div className="space-y-4">
                        <p className="text-base leading-relaxed">
                            <span className="font-semibold text-gray-700">Getting started:</span><br />
                            Your student profile will be automatically created when you enroll in your first course. 
                            You'll be able to access and update your profile information at any time.
                        </p>

                        <p className="text-sm text-gray-500 italic border-l-4 border-blue-100 pl-3 py-2">
                            Use the navigation menu above to access different portal features. 
                            
                        </p>
                    </div>
                </div>

                <button className="hidden mt-6 bg-blue-600 hover:bg-blue-700 text-white font-medium px-6 py-3 rounded-lg 
                              transition-all duration-200 transform hover:scale-[1.02]">
                    Check Eligibility
                </button>
            </div>
        </div>
    );
}