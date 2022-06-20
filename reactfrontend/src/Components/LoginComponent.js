import React, {useState} from "react";
import {Button, Form, FormGroup, Label, Input} from 'reactstrap'
import PropTypes from 'prop-types';

import { FormErrors } from './FormErrors';



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
        <div className="Login">
            <h2>Admin Log In</h2>
        <Form onSubmit={handleSubmit}>
            <FormGroup size="lg" controlId="text">
                <Label>Username</Label>
                <Input type="text" name="username" id="username" onChange={e => setUserName(e.target.value)}/>            
            </FormGroup>
            
            <FormGroup size="lg" controlId="password">
                <Label>Password</Label>
                <Input type="password" name="password" id="password" onChange={e => setPassword(e.target.value)} />            
            </FormGroup>
            <Button color="primary" outline type="submit">Login</Button>
            <div className="panel panel-default">
                <FormErrors formErrors={error} />
            </div>

        </Form>
        </div>
    );
}

Login.propTypes = {
    setToken: PropTypes.func.isRequired
  }
