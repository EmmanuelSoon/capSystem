import React, {useState} from "react";
import {Button, Form, FormGroup, Label, Input, Row} from 'reactstrap'
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
        <div>
            <div className="login">
                <Row>
                    <h2><strong>Admin Log In</strong></h2>
                </Row>
                <Row>
                    <Form className="loginform" onSubmit={handleSubmit}>
                        <FormGroup className="mb-3" size="lg" controlId="text">
                            <Label>Username</Label>
                            <Input type="text" name="username" id="username" onChange={e => setUserName(e.target.value)}/>            
                        </FormGroup>
                        
                        <FormGroup className="mb-3" size="lg" controlId="password">
                            <Label>Password</Label>
                            <Input type="password" name="password" id="password" onChange={e => setPassword(e.target.value)} />            
                        </FormGroup>
                        <Button color="primary" outline type="submit">Login</Button>
                        <div className="panel panel-default">
                            <FormErrors formErrors={error} />
                        </div>
                    </Form>
                </Row>
                <Button href="http://localhost:8080">Student/Lecturer Login</Button>
            </div>
        </div>


    );
}

Login.propTypes = {
    setToken: PropTypes.func.isRequired
  }
