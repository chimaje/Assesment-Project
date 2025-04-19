/* eslint-disable no-unused-vars */
export function Books() {
    return (
        <div className="min-h-screen bg-gray-50 pt-16"> {/* Added pt-16 for header spacing */}
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8"> {/* Consistent container */}
                <div className="text-center mb-10 space-y-2"> {/* Improved spacing */}
                    <h1 className="text-3xl font-bold text-gray-900 tracking-tight">
                        Borrowed Books
                    </h1>
                    <p className="text-gray-500 text-lg">
                        Manage your current borrowings
                    </p>
                </div>
                
                {/* Card matching Portal component styling */}
                <div className="bg-white rounded-2xl shadow-lg p-8 max-w-md mx-auto transition-all hover:shadow-xl duration-300">
                    <div className="text-center space-y-6"> {/* Consistent spacing */}
                        <div className="inline-flex bg-blue-100 p-4 rounded-full"> {/* Matching Portal's emoji style */}
                            <span className="text-4xl">📚</span>
                        </div>
                        
                        <div className="space-y-4"> {/* Vertical spacing */}
                            <p className="text-gray-600 text-lg leading-relaxed">
                                You currently have <span className="font-semibold">0 books</span> borrowed.
                            </p>
                            <p className="text-gray-500 text-base">
                                Visit the Library Portal to explore and borrow books.
                            </p>
                        </div>

                        <a 
                            onClick={() => {
                                // Open library login in new tab
                                const loginWindow = window.open(
                                    'http://localhost',
                                    '_blank',
                                );
                            }}
                            className="inline-flex items-center justify-center bg-blue-600 hover:bg-blue-700 text-white 
                                    font-medium py-3 px-6 rounded-lg transition-all duration-200 hover:scale-[1.02]
                                    focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2"
                        >
                            Explore Library
                        </a>
                    </div>
                </div>
            </div>
        </div>
    );
}