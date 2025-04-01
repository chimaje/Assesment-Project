import React from "react";

export function Portal(){
    return(
        <div className="align text-justify pl-6 pt-3">
            <div className="text-black text-justify  " >
                <h3 className="text-xl font-bold ">
                    Hello, [Studentname]!
                </h3>
                <div className=" p-[5px]" >
                    <p className=" p-[5px]" >
                        Welcome to your portal
                    </p>
                    <p className=" p-[5px]" >
                        Here , you have to register as a student  which happens automatically <br/>
                        upon enrolling in your first course , you will be to access and edit your student profile<br/>
                    </p>
                    <p className=" p-[5px]" >
                        You will also be able to see the courses you are enrolled in
                    </p>
                    <p className=" p-[5px] text-sm" >
                        Use the navigation bar above to access the different features of your portal
                    </p>
            </div>

            </div>
            <button className="hidden">Check Elegibilty</button>
        </div>

    )
}