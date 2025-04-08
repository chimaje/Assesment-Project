/* eslint-disable no-unused-vars */
import React , {useState , useEffect} from  "react";
import { userService,courseService } from '../../services/api.js';

export function AllCourses(){
    const [courses, setCourses] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null)

    useEffect(() => {
        const loadCourses = async () => {
            try {
                const data = await courseService.getAllCourses();
                
                setCourses(data);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };


        loadCourses();
    }, []);
    const viewDetails = (courseId) => {
        console.log(`Viewing details for course ${courseId}`);
        
    };

const handleEnroll = async (courseId) => {
        try {
            let username =  userService.getCurrentUser();
            if (!username) {
                throw new Error('You must be logged in to enroll');
            }
            await courseService.enrollInCourse(courseId, username);
            alert('Enrollment successful!');
        } catch (error) {
            setError(error.message);
        }
    };

    return(
        <div>
            <div className="flex justify-start text-4xl text-black m-3">
                <h2>All Courses</h2>
            </div>
            <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6 m-2">
                {courses.map((course) => (
                    <div
                        key={course.id}
                        className="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-shadow duration-300"
                    >
                        <div className="p-6">
                            <h2 className="text-xl font-semibold text-gray-800 mb-2">{course.title}</h2>
                            <p className="text-gray-600 mb-4">{course.description}</p>

                            <div className="flex justify-between items-center">
                                <span className="font-bold text-gray-700">
                                  £{course.fee?.toFixed(2) || '0.00'}
                                </span>
                            </div>

                            <div className="mt-4 flex space-x-2">
                                <button
                                    className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition-colors"
                                    onClick={() => handleEnroll(course.id)}
                                >
                                    Enroll
                                </button>
                                <button
                                    className="px-4 py-2 border border-gray-300 text-gray-700 rounded hover:bg-gray-50 transition-colors"
                                    onClick={() => viewDetails(course.id)}
                                >
                                    Details
                                </button>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    )
}