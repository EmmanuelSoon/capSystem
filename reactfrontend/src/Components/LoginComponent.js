import React, {useState} from "react";
import {Button, Form, FormGroup, Label, Input, Row} from 'reactstrap'
import PropTypes from 'prop-types';

import { FormErrors } from './FormErrors';
import logo from '../assets/images/logo.jpg'



async function loginUser(credentials) {
    return fetch('/admin/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(credentials)
    })
      .then(data => data.json())
}



export default function Login({setToken}) {

    const [username, setUserName] = useState();
    const [password, setPassword] = useState();

    const [error, setError] = useState({Error:''});


    const handleSubmit = async e => {
        e.preventDefault();
        const token = await loginUser({
          username,
          password
        })
        if(token.status == null){
            setToken(token);
        }
        else{
            setError({Error:" Invalid Login"});
        }
      }

    return (
        <div>
            <div className="container">
                <div className="row">
                    <div className="col-md-6 offset-md-3">
                        <h2 className="text-center text-dark mt-5"><strong>Admin Login Form</strong></h2>
                        <div className="card my-5">
                        <form className="card-body cardbody-color p-lg-5" onSubmit={handleSubmit}>

                            <div className="text-center">
                            <img src={logo} class="img-fluid profile-image-pic img-thumbnail rounded-circle my-3"
                                width="200px" alt="profile"/>
                            </div>

                            <div className="mb-3">
                                <Input type="text" name="username" id="username" onChange={e => setUserName(e.target.value)} placeholder="User Name"/>            
                            </div>
                            <div className="mb-3">
                                <Input type="password" name="password" id="password" onChange={e => setPassword(e.target.value)} placeholder="Password" />            
                            </div>
                            <div className="text-center"><button type="submit" className="btn btn-color px-5 w-100">Login</button></div>
                            <div className="panel panel-default">
                                            <FormErrors formErrors={error} />
                            </div>
                            <div id="emailHelp" class="form-text text-center mb-5 mt-5 text-dark">
                                Not an Admin? <a href="http://localhost:8080" class="text-dark fw-bold"> Student/Lecturer Login</a>
                            </div>
                        </form>
                        </div>

                    </div>
                </div>
            </div>
    </div>


    );
}

Login.propTypes = {
    setToken: PropTypes.func.isRequired
  }
