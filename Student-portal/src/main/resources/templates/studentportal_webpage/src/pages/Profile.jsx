import { useState } from "react";
import {  useNavigate} from 'react-router-dom';
import { userService } from '../services/api.js';


export function Profile() {
    const [showEligibility, setShowEligibility] = useState(false);
    const [isEligible] = useState(false); // Simulate state
     const username =  userService.getCurrentUser();
     const navigate = useNavigate();
  
    return (
      <div className="min-h-screen bg-gray-50 pt-16">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
            {/* Student Profile Section */}
            <div className="bg-white rounded-2xl shadow-lg p-8">
              <h2 className="text-2xl font-bold text-gray-900 mb-6">Student Profile</h2>
              <div className="space-y-4">
                <div className="flex items-center gap-4">
                  <div className="bg-blue-100 p-3 rounded-full">
                    <span className="text-xl">👤</span>
                  </div>
                  <div>
                    <p className="font-medium text-gray-900">{username }</p>
                  </div>
                </div>
                <div className="space-y-2">
                  <p className="text-gray-600"><span className="font-semibold">Student ID:</span> 123456</p>
                </div>
              </div>
            </div>
  
  
            {/* Eligibility Section */}
            <div className="lg:col-span-2 bg-white rounded-2xl shadow-lg p-8">
              <div className="flex flex-col md:flex-row items-center justify-between gap-6">
                <div className="flex items-center gap-4">
                  <div className="bg-blue-100 p-3 rounded-full">
                    <span className="text-xl">🎓</span>
                  </div>
                  <div>
                    <h3 className="text-xl font-semibold text-gray-900">Graduation Eligibility</h3>
                    <p className="text-gray-500">Check your graduation requirements status</p>
                  </div>
                </div>
                <button 
                  onClick={() => {setShowEligibility(true), navigate("/eligibility_check");}}
                  className="w-full md:w-auto bg-blue-600 hover:bg-blue-700 text-white font-medium py-3 px-8 rounded-lg 
                         transition-all duration-200 transform hover:scale-[1.02]"
                >
                  Check Eligibility
                </button>
              </div>
  
              {showEligibility && (
                <div className="mt-8 space-y-6">
                  {!isEligible && (
                    <div className="bg-red-50 p-6 rounded-lg border border-red-100">
                      <div className="flex items-center gap-3 mb-4">
                        <div className="bg-red-100 p-2 rounded-full">
                          <span className="text-red-600 text-xl">⚠️</span>
                        </div>
                        <h4 className="text-lg font-semibold text-red-700">Eligibility Requirements Not Met</h4>
                      </div>
                      
                      <div className="space-y-3">
                        <p className="text-red-600">Missing requirements:</p>
                        <ul className="list-disc pl-6 space-y-2">
                          <li className="text-red-600">
                            <span className="font-medium">Outstanding Invoices:</span>
                            <div className="mt-2 space-y-2">
                              <div className="flex items-center justify-between bg-white p-3 rounded-lg">
                                <span>Tuition Fee - Fall 2023</span>
                                <a href="/invoices" className="text-blue-600 hover:underline">Pay Now</a>
                              </div>
                              <div className="flex items-center justify-between bg-white p-3 rounded-lg">
                                <span>Library Fine - Spring 2024</span>
                                <a href="/invoices" className="text-blue-600 hover:underline">Pay Now</a>
                              </div>
                            </div>
                          </li>
                          <li className="text-red-600">Incomplete course requirements</li>
                        </ul>
                      </div>
                    </div>
                  )}
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
    );
  }