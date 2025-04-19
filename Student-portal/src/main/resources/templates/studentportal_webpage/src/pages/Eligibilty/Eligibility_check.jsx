import { useState } from "react";

export function EligibilityCheck() {
  const [isEligible] = useState(true); // Simulate state

  return (
    <div className="min-h-screen bg-gray-50 pt-16">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <div className="bg-white rounded-2xl shadow-lg p-8 max-w-3xl mx-auto">
          <div className="text-center mb-8">
            <div className="inline-flex bg-blue-100 p-4 rounded-full mb-4">
              <span className="text-4xl">🎓</span>
            </div>
            <h1 className="text-3xl font-bold text-gray-900 mb-2">
              Graduation Eligibility Status
            </h1>
            <p className="text-gray-500">Check your eligibility for graduation</p>
          </div>

          {!isEligible ? (
            <div className="space-y-6">
              <div className="bg-red-50 p-6 rounded-lg border border-red-100">
                <div className="flex items-center gap-3 mb-4">
                  <div className="bg-red-100 p-2 rounded-full">
                    <span className="text-red-600 text-xl">⚠️</span>
                  </div>
                  <h4 className="text-lg font-semibold text-red-700">Not Eligible for Graduation</h4>
                </div>
                
                <div className="space-y-4">
                  <p className="text-red-600 font-medium">Missing Requirements:</p>
                  <div className="space-y-3">
                    <div className="flex items-center gap-3">
                      <span className="text-red-500">✖</span>
                      <span className="text-gray-600">All fees paid (2 outstanding invoices)</span>
                      <a href="/invoices" className="ml-auto text-blue-600 hover:underline">View Invoices</a>
                    </div>
                    <div className="flex items-center gap-3">
                      <span className="text-red-500">✖</span>
                      <span className="text-gray-600">Completed 120 credits (current: 110)</span>
                    </div>
                    <div className="flex items-center gap-3">
                      <span className="text-green-500">✔</span>
                      <span className="text-gray-600">Minimum GPA of 2.0 (current: 3.4)</span>
                    </div>
                  </div>
                </div>
              </div>

              <div className="mt-8 text-center">
                <a
                  href="/profile"
                  className="inline-block bg-blue-600 hover:bg-blue-700 text-white 
                         font-medium py-3 px-8 rounded-lg transition-all duration-200"
                >
                  Return to Portal
                </a>
              </div>
            </div>
          ) : (
            <div className="text-center space-y-6">
              <div className="bg-green-50 p-6 rounded-lg border border-green-100">
                <div className="flex items-center gap-3 justify-center">
                  <div className="bg-green-100 p-2 rounded-full">
                    <span className="text-green-600 text-xl">🎉</span>
                  </div>
                  <h4 className="text-lg font-semibold text-green-700">Eligible for Graduation!</h4>
                </div>
              </div>
              <p className="text-gray-600">Congratulations! You've met all graduation requirements.</p>
              <div className="mt-8 text-center">
                <a
                  href="/profile"
                  className="inline-block bg-blue-600 hover:bg-blue-700 text-white 
                         font-medium py-3 px-8 rounded-lg transition-all duration-200"
                >
                  Return to Portal
                </a>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}