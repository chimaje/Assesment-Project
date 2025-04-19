import { useState , useEffect} from "react";
import {  useNavigate} from 'react-router-dom';
import { studentService, userService } from '../services/api.js';


export function Profile() {
  const navigate = useNavigate();
  const [isEditing, setIsEditing] = useState(false);
  const [student, setStudent] = useState(null);
  const [formData, setFormData] = useState({ surname: '', forename: '' });
  const [showEligibility, setShowEligibility] = useState(false);
  const [isEligible] = useState(true); // Simulate state
  const username = userService.getCurrentUser();

  useEffect(() => {
      const fetchStudent = async () => {
          try {
              const studentData = await studentService.getStudentByUser(username);
              if (studentData) {
                  setStudent(studentData);
                  setFormData({
                      surname: studentData.surname,
                      forename: studentData.forename
                  });
              }
          } catch (error) {
              console.error("Failed to fetch student:", error);
          }
      };

      if (username) fetchStudent();
  }, [username]);

  const handleEditToggle = () => {
      setIsEditing(!isEditing);
      if (isEditing) {
          // Reset form on cancel
          setFormData({
              surname: student.surname,
              forename: student.forename
          });
      }
  };

  const handleSave = async () => {
      try {
          const updatedStudent = await studentService.updateStudentInfo(
              student.externalStudentId,
              formData
          );
          setStudent(updatedStudent);
          setIsEditing(false);
      } catch (error) {
          console.error("Failed to update student:", error);
      }
  };

  const handleInputChange = (e) => {
      setFormData({
          ...formData,
          [e.target.name]: e.target.value
      });
  };
  
    return (
      <div className="min-h-screen bg-gray-50 pt-16">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
            {/* Student Profile Section */}
            {/* <div className="bg-white rounded-2xl shadow-lg p-8">
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
                  <p className="text-gray-600"><span className="font-semibold">Student ID:</span>{studentid}</p>
                </div>
              </div>
            </div> */}
                    {/* Student Profile Section */}
                    {/* <div className="bg-white rounded-2xl shadow-lg p-8 relative">
                        <h2 className="text-2xl font-bold text-gray-900 mb-6">Student Profile</h2>
                        <div className="space-y-4">
                            <div className="flex items-center gap-4">
                                <div className="bg-blue-100 p-3 rounded-full">
                                    <span className="text-xl">👤</span>
                                </div>
                                <div>
                                    <p className="font-medium text-gray-900">{username}</p>
                                </div>
                            </div>
                            <div className="space-y-2">
                                <p className="text-gray-600">
                                    <span className="font-semibold">Student ID:</span> 
                                    {student?.externalStudentId || 'Loading...'}
                                </p>
                                <div className="mt-4 space-y-3">
                                    <div className="flex items-center gap-2">
                                        <span className="font-semibold w-20">Surname:</span>
                                        {isEditing ? (
                                            <input
                                                name="surname"
                                                value={formData.surname}
                                                onChange={handleInputChange}
                                                className="border rounded p-1 flex-1"
                                            />
                                        ) : (
                                            <span>{student?.surname}</span>
                                        )}
                                    </div>
                                    <div className="flex items-center gap-2">
                                        <span className="font-semibold w-20">Forename:</span>
                                        {isEditing ? (
                                            <input
                                                name="forename"
                                                value={formData.forename}
                                                onChange={handleInputChange}
                                                className="border rounded p-1 flex-1"
                                            />
                                        ) : (
                                            <span>{student?.forename}</span>
                                        )}
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="mt-6 flex gap-2">
                            {isEditing ? (
                                <>
                                    <button
                                        onClick={handleSave}
                                        className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700"
                                    >
                                        Save
                                    </button>
                                    <button
                                        onClick={handleEditToggle}
                                        className="bg-gray-600 text-white px-4 py-2 rounded hover:bg-gray-700"
                                    >
                                        Cancel
                                    </button>
                                </>
                            ) : (
                                <button
                                    onClick={handleEditToggle}
                                    className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
                                    disabled={!student}
                                >
                                    Edit Profile
                                </button>
                            )}
                        </div>
                    </div>
   */}

             {/* Student Profile Section */}
             <div className="bg-white rounded-2xl shadow-lg p-8">
                        <div className="flex justify-between items-start mb-6">
                            <h2 className="text-2xl font-bold text-gray-900">Student Profile</h2>
                            {!isEditing ? (
                                <button
                                    onClick={handleEditToggle}
                                    className="text-blue-600 hover:text-blue-800 font-medium flex items-center gap-2"
                                >
                                    <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
                                        <path d="M13.586 3.586a2 2 0 112.828 2.828l-.793.793-2.828-2.828.793-.793zM11.379 5.793L3 14.172V17h2.828l8.38-8.379-2.83-2.828z" />
                                    </svg>
                                    Edit Profile
                                </button>
                            ) : (
                                <div className="flex gap-2">
                                    <button
                                        onClick={handleSave}
                                        className="px-4 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700 transition-colors"
                                    >
                                        Save Changes
                                    </button>
                                    <button
                                        onClick={handleEditToggle}
                                        className="px-4 py-2 bg-gray-200 text-gray-700 rounded-lg hover:bg-gray-300 transition-colors"
                                    >
                                        Cancel
                                    </button>
                                </div>
                            )}
                        </div>

                        <div className="space-y-6">
                            <div className="flex items-center gap-4">
                                <div className="bg-blue-100 p-3 rounded-full">
                                    <span className="text-xl">👤</span>
                                </div>
                                <div>
                                    <p className="text-lg font-medium text-gray-900">{username}</p>
                                    <p className="text-sm text-gray-500">
                                        Student ID: {student?.externalStudentId || 'Loading...'}
                                    </p>
                                </div>
                            </div>

                            <div className="border-t pt-6 space-y-4">
                                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                                    <div>
                                        <label className="block text-sm font-medium text-gray-700 mb-2">
                                            Surname
                                        </label>
                                        {isEditing ? (
                                            <input
                                                name="surname"
                                                value={formData.surname}
                                                onChange={handleInputChange}
                                                className="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                                            />
                                        ) : (
                                            <p className="text-gray-900">{student?.surname}</p>
                                        )}
                                    </div>
                                    <div>
                                        <label className="block text-sm font-medium text-gray-700 mb-2">
                                            Forename
                                        </label>
                                        {isEditing ? (
                                            <input
                                                name="forename"
                                                value={formData.forename}
                                                onChange={handleInputChange}
                                                className="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                                            />
                                        ) : (
                                            <p className="text-gray-900">{student?.forename}</p>
                                        )}
                                    </div>
                                </div>
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
                  onClick={() => {setShowEligibility(true), navigate("/home/eligibility_check");}}
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