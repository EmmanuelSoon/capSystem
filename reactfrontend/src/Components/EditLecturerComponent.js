import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';

import { FormErrors } from './FormErrors';


class EditLecturer extends Component {

    emptyItem = {
        name: '',
        username: '',
        password: '',
        email: '',
        active: true,
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem,
            emailValid: false,
            passwordValid: false,
            usernameValid: false,
            nameValid: false,
            formValid: false,
            formErrors: {email:'', password:'', username: '', name:''},
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }


    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            const lecturer = await (await fetch(`/admin/lecturer/${this.props.match.params.id}`)).json();
            lecturer.active = true;
            this.setState({item: lecturer, formValid: true, emailValid: true, passwordValid: true, usernameValid: true, nameValid: true});
        }
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item},
            () => {this.validateField(name, value)});
    }

    
    validateField(fieldName, value) {
        let fieldValidationErrors = this.state.formErrors;
        let emailValid = this.state.emailValid;
        let passwordValid = this.state.passwordValid;
        let nameValid = this.state.nameValid;
        let usernameValid = this.state.usernameValid;
    
        switch(fieldName) {
            case 'email':
                emailValid = value.match(/^([\w.%+-]+)@([\w-]+\.)+([\w]{2,})$/i);
                fieldValidationErrors.email = emailValid ? '' : ' is invalid';
                break;
            case 'password':
                passwordValid = value.length >= 6;
                fieldValidationErrors.password = passwordValid ? '': ' needs to be more than 6 characters';
                break;
            case 'name':
                nameValid = value.length >= 3 && value.length <= 15;
                fieldValidationErrors.name = nameValid ? '' : ' needs to be within 3 to 15 characters';
                break;
            case 'username':
                usernameValid = value.length >= 3 && value.length <= 15;
                fieldValidationErrors.username = usernameValid ? '' : ' needs to be within 3 to 15 characters';
                break;
            default:
                break;
        }
        this.setState({formErrors: fieldValidationErrors,
                        emailValid: emailValid,
                        passwordValid: passwordValid,
                        nameValid: nameValid,
                        usernameValid: usernameValid
                      }, this.validateForm);
      }

    validateForm() {
        this.setState({formValid: this.state.emailValid && this.state.passwordValid && this.state.nameValid && this.state.usernameValid});
    }

    errorClass(error) {
        return(error.length === 0 ? '' : 'has-error');
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;
    
        await fetch('/admin/lecturer' + (item.lecturerId ? '/' + item.lecturerId : ''), {
            method: (item.lecturerId) ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        });
        this.props.history.push('/admin/lecturer');
    }

    render() {
        const {item} = this.state;
        const title = <h2 className='mb-3 mt-3'>{item.lecturerId ? 'Edit Lecturer' : 'Add Lecturer'}</h2>;
    
        return <div>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup className={`${this.errorClass(this.state.formErrors.name)}`}>
                        <Label for="name">Name</Label>
                        <Input type="text" name="name" id="name" value={item.name || ''}
                               onChange={this.handleChange} autoComplete="name"/>
                    </FormGroup>
                    <FormGroup className={`${this.errorClass(this.state.formErrors.username)}`}>
                        <Label for="username">Username</Label>
                        <Input type="text" name="username" id="username" value={item.username || ''}
                               onChange={this.handleChange} autoComplete="username"  disabled={item.lecturerId ? true : false}/>
                    </FormGroup>
                    <FormGroup className={`${this.errorClass(this.state.formErrors.password)}`}>
                        <Label for="password">Password</Label>
                        <Input type="text" name="password" id="password" value={item.password || ''}
                               onChange={this.handleChange} autoComplete="password"/>
                    </FormGroup>
                    <FormGroup className={`${this.errorClass(this.state.formErrors.email)}`}>
                        <Label for="email">Email</Label>
                        <Input type="text" name="email" id="email" value={item.email || ''}
                               onChange={this.handleChange} autoComplete="email"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="active">Current Status</Label>
                            <Input type='select' name="active" id="active" value={item.active || ''}
                               onChange={this.handleChange}>
                                <option value={true}>True</option>
                                <option value={false}>False</option>
                            </Input>
                    </FormGroup>
                    <div className="panel panel-default">
                        <FormErrors formErrors={this.state.formErrors} />
                    </div>
                    <FormGroup>
                        <Button color="primary" type="submit" disabled={!this.state.formValid}>Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/admin/lecturer">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }


}
export default withRouter(EditLecturer);