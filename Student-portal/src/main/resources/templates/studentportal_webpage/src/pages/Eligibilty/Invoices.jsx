import { useEffect, useState } from 'react';
import { studentService, userService ,financeService} from '../../services/api.js';

export  function InvoicePage() {
    const [invoices, setInvoices] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    
    // Get student ID from user service - adjust this based on your user structure
    const studentId = userService.getCurrentUser()?.student?.externalStudentId;

    useEffect(() => {
        const loadInvoices = async () => {
            const username = userService.getCurrentUser()
            const studentData = await studentService.getStudentByUser(username);
            const studentId = studentData?.externalStudentId ;
            if (!studentId) {
                setError('Student ID not found');
                return;
            }

            try {
                const data = await financeService.getInvoices(studentId);
                setInvoices(data);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        loadInvoices();
    }, [studentId]);

    const handlePayment = async (reference) => {
        try {
            await financeService.payInvoice(reference);
            // Refresh invoices after successful payment
            const updatedInvoices = await financeService.getInvoices(studentId);
            setInvoices(updatedInvoices);
        } catch (err) {
            setError(err.message);
        }
    };

    const handleCancellation = async (reference) => {
        try {
            await financeService.cancelInvoice(reference);
            // Refresh invoices after successful cancellation
            const updatedInvoices = await financeService.getInvoices(studentId);
            setInvoices(updatedInvoices);
        } catch (err) {
            setError(err.message);
        }
    };

    if (loading) {
        return <div className="text-center py-8">Loading invoices...</div>;
    }

    if (error) {
        return <div className="text-center py-8 text-red-500">{error}</div>;
    }

    return (
        <div className="min-h-screen bg-gray-50 py-8 px-4 sm:px-6 lg:px-8">
            <div className="max-w-4xl mx-auto">
                <h1 className="text-3xl font-bold text-gray-900 mb-8">Your Invoices</h1>
                
                <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                    {invoices.map((invoice) => (
                        <div key={invoice.id} className="bg-white rounded-lg shadow-md p-6">
                            {/* Rest of the UI remains the same as previous version */}
                            <div className="flex justify-between items-start mb-4">
                                <div>
                                    <h2 className="text-xl font-semibold text-gray-900">
                                        Reference: {invoice.reference}
                                    </h2>
                                    <p className="text-gray-600">
                                        Due Date: {new Date(invoice.dueDate).toLocaleDateString()}
                                    </p>
                                </div>
                                <span className={`px-3 py-1 rounded-full text-sm font-medium ${
                                    invoice.status === 'OUTSTANDING' 
                                        ? 'bg-red-100 text-red-800' 
                                        : 'bg-green-100 text-green-800'
                                }`}>
                                    {invoice.status}
                                </span>
                            </div>

                            <div className="border-t pt-4">
                                <div className="grid grid-cols-2 gap-4 mb-4">
                                    <div>
                                        <p className="text-sm text-gray-600">Amount</p>
                                        <p className="text-lg font-medium text-gray-900">
                                            £{invoice.amount.toFixed(2)}
                                        </p>
                                    </div>
                                    <div>
                                        <p className="text-sm text-gray-600">Type</p>
                                        <p className="text-lg font-medium text-gray-900">
                                            {invoice.type.replace('_', ' ')}
                                        </p>
                                    </div>
                                </div>

                                {invoice.status === 'OUTSTANDING' && (
                                    <div className="flex gap-4">
                                        <button
                                            onClick={() => handlePayment(invoice.reference)}
                                            className="px-4 py-2 bg-green-600 text-white rounded-md hover:bg-green-700 transition-colors"
                                        >
                                            Pay Now
                                        </button>
                                        <button
                                            onClick={() => handleCancellation(invoice.reference)}
                                            className="px-4 py-2 bg-red-600 text-white rounded-md hover:bg-red-700 transition-colors"
                                        >
                                            Cancel Invoice
                                        </button>
                                    </div>
                                )}
                            </div>
                        </div>
                    ))}

                    {invoices.length === 0 && (
                        <div className="text-center py-8 text-gray-500">
                            No invoices found
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
}