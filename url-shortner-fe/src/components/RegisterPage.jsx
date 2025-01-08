import { useForm } from "react-hook-form";
import TextField from "./TextField";
import { useState } from "react";
import { Link } from "react-router-dom";

function RegisterPage() {

    const [loader, setLoader] = useState(false);

    const {
        register,
        handleSubmit,
        reset,
        formState: { errors },
    } = useForm({
         defaultValues: {
            username: "",
            email: "",
            password: ""
        },
        mode: "onTouched"
    });

    const registerHandler = async (data) => {

    };

  return (
    <div className="min-h-[calc(100vh-64px)] flex justify-center items-center">
        <form 
            onSubmit={handleSubmit(registerHandler)}
            className="sm:w-[450px] w-[360px]  shadow-custom py-8 sm:px-8 px-4 rounded-md"
        >
            <h1 className="text-center font-serif text-btnColor font-bold lg:text-3xl text-2xl">
                Register Here
            </h1>

            <hr className="mt-2 mb-5 text-black"/>
            <div className="flex flex-col gap-3">
                <TextField
                    label="Username"
                    required
                    id="username"
                    type="text"
                    errors={errors}
                    register={register}
                    message="*Username is required"
                    className="w-full"
                    placeholder="Type your username"
                />

                <TextField
                    label="Email"
                    required
                    id="email"
                    type="email"
                    errors={errors}
                    register={register}
                    message="*Email is required"
                    className="w-full"
                    placeholder="Type your email"
                />

                <TextField
                    label="Password"
                    required
                    id="password"
                    type="password"
                    errors={errors}
                    register={register}
                    message="*Password is required"
                    className="w-full"
                    min={6}
                    placeholder="Type your password"
                />
            </div>

            <button
                disabled={loader}
                type="submit"
                className="bg-customRed font-semibold text-white  bg-custom-gradient w-full py-2 hover:text-slate-400 transition-colors duration-100 rounded-sm my-3"
            >
                {loader ? "Loading ..." : "Register"}
            </button>
            <p className="text-center text-sm text-slate-700 mt-6">
                Already have an account? 
                <Link 
                    to="/login"
                    className="font-semibold text-sm underline hover:text-black"
                >
                    <span className="text-btnColor">Login</span>
                </Link>
            </p>
        </form>
    </div>
  )
}

export default RegisterPage
