import { useState, useEffect } from "react";
import { useNavigate } from 'react-router-dom';
import { financeService, userService ,studentService } from '../../services/api.js'; // Add financeService import

export function EligibilityCheck() {
  const [isEligible, setIsEligible] = useState(false);
  const [outstandingInvoices, setOutstandingInvoices] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const checkEligibility = async () => {
      try {
        const currentUser = userService.getCurrentUser();
        const studentData = await studentService.getStudentByUser(currentUser);
        const studentId = studentData?.externalStudentId ;
        if (!studentId) {
          throw new Error('Student ID not found');
        }

        // Get invoices from finance service
        const invoices = await financeService.getInvoices(studentId);
        
        // Check for outstanding invoices
        const outstanding = invoices.filter(invoice => 
          invoice.status === 'OUTSTANDING'
        );
        
        setOutstandingInvoices(outstanding);
        setIsEligible(outstanding.length === 0);
      } catch (err) {
        setError(err.message || 'Failed to check eligibility');
      } finally {
        setIsLoading(false);
      }
    };

    checkEligibility();
  }, []);

  if (isLoading) {
    return (
      <div className="min-h-screen bg-gray-50 pt-16 flex items-center justify-center">
        <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-blue-500"></div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="min-h-screen bg-gray-50 pt-16 flex items-center justify-center">
        <div className="text-red-500 text-center">
          <p className="text-xl mb-4">Error checking eligibility:</p>
          <p className="text-lg">{error}</p>
        </div>
      </div>
    );
  }

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
                    {outstandingInvoices.length > 0 && (
                      <div className="flex items-center gap-3">
                        <span className="text-red-500">✖</span>
                        <span className="text-gray-600">
                          All fees paid ({outstandingInvoices.length} outstanding invoices)
                        </span>
                        <a 
                          href="/home/invoices" 
                          className="ml-auto text-blue-600 hover:underline"
                        >
                          View Invoices
                        </a>
                      </div>
                    )}
                    {/* Add other eligibility criteria here */}
                  </div>
                </div>
              </div>

              <div className="mt-8 text-center">
                <button
                  onClick={() => navigate(-1)}
                  className="inline-block bg-blue-600 hover:bg-blue-700 text-white 
                         font-medium py-3 px-8 rounded-lg transition-all duration-200"
                >
                  Return to Portal
                </button>
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
                <button
                  onClick={() => navigate(-1)}
                  className="inline-block bg-blue-600 hover:bg-blue-700 text-white 
                         font-medium py-3 px-8 rounded-lg transition-all duration-200"
                >
                  Return to Portal
                </button>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}