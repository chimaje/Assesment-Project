import { useState, useEffect } from 'react';
import { courseService, userService } from '../../services/api.js';
import { useNavigate } from 'react-router-dom';

export function My_Courses() {
    const [courses, setCourses] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const fetchCourses = async () => {
            try {
                const currentUser = userService.getCurrentUser();
                if (!currentUser) {
                    navigate('/log_in');
                    return;
                }

                const data = await courseService.getEnrolledCourses(currentUser);
                setCourses(data);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchCourses();
    }, [navigate]);

    if (loading) return <div className="text-center mt-8">Loading enrolled courses...</div>;
    if (error) return <div className="text-red-500 text-center mt-8">{error}</div>;

    return (
        <div className="max-w-4xl mx-auto p-4 mt-6">
            <h1 className="text-2xl font-bold mb-6 text-black">Your Courses</h1>
            
            {courses.length === 0 ? (
                <div className="text-gray-500 text-center">
                    You haven't enrolled in any courses yet.
                </div>
            ) : (
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                    {courses.map(course => (
                        <div key={course.id} className="border rounded-lg p-4 shadow-sm hover:shadow-md transition-shadow">
                            <h2 className="text-3xl font-semibold mb-2 text-black">{course.title}</h2>
                            <p className="text-cyan-600 text-[15px]">{course.description}</p>
                            <div className="mt-3 text-sm text-gray-500">
                                {/* <p>Course Code: {course.code}</p> */}
                                <p>Course Fee: {course.fee}</p>
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
}