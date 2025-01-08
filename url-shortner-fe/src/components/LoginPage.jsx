import { useForm } from "react-hook-form";
import TextField from "./TextField";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../../public/api/api";
import toast from "react-hot-toast";

function LoginPage() {

    const navigate = useNavigate();
    const [loader, setLoader] = useState(false);

    const {
        register,
        handleSubmit,
        reset,
        formState: { errors },
    } = useForm({
         defaultValues: {
            username: "",
            password: ""
        },
        mode: "onTouched"
    });

    const loginHandler = async (data) => {
        setLoader(true);
        console.log(data);
        try {
            const {data: response} = await api.post(
                "/api/auth/public/login", 
                data
            );

            console.log(response.token);

            // store token in local storage
            localStorage.setItem("JWT_TOKEN", JSON.stringify(response.token));

            toast.success("Login Successfully");

            reset();
            navigate('/')
        } catch (error) {
            console.log(error);
            toast.error("Login failed");
        } finally {
            setLoader(false);
        }
    };

  return (
    <div className="min-h-[calc(100vh-64px)] flex justify-center items-center">
        <form 
            onSubmit={handleSubmit(loginHandler)}
            className="sm:w-[450px] w-[360px]  shadow-custom py-8 sm:px-8 px-4 rounded-md"
        >
            <h1 className="text-center font-serif text-btnColor font-bold lg:text-3xl text-2xl">
                Login Here
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
                {loader ? "Loading ..." : "Login"}
            </button>
            <p className="text-center text-sm text-slate-700 mt-6">
                Dont have an account? 
                <Link 
                    to="/register"
                    className="font-semibold text-sm underline hover:text-black"
                >
                    <span className="text-btnColor">Register</span>
                </Link>
            </p>
        </form>
    </div>
  )
}

export default LoginPage;
