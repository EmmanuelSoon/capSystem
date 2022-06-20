import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import { FormErrors } from './FormErrors';


class EditCourse extends Component {

    item = {
        name: '',
        description: ''
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.item,
            descValid: false,
            nameValid: false,
            formValid: false,
            formErrors: {
                name:'',
                description:''
            }
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            const course = await (await fetch(`/admin/course/${this.props.match.params.id}`)).json();
            this.setState({item: course, formValid: true});
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
        let descValid = this.state.descValid;
        let nameValid = this.state.nameValid;

        switch(fieldName) {
            case 'name':
                nameValid = value.length >= 3 && value.length <= 15;
                fieldValidationErrors.name = nameValid ? '' : ' needs to be within 3 to 15 characters';
                break;
            case 'description':
                descValid = value.length >= 10;
                fieldValidationErrors.description = descValid ? '' : ' needs to be at least 10 characters';
                break;
            default:
                break;
        }
        this.setState({formErrors: fieldValidationErrors,
            descValid: descValid,
            nameValid: nameValid,
        }, this.validateForm);
    }

    validateForm() {
        this.setState({formValid: this.state.nameValid && this.state.descValid});
    }

    errorClass(error) {
        return(error.length === 0 ? '' : 'has-error');
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;
        console.log(item)
        await fetch('/admin/course' + (item.courseId ? ('/' + item.courseId) : ''), {

            method: (item.studentId) ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        });
        this.props.history.push('/admin/courselist');
    }


    render() {
        const {item} = this.state;
        const title = <h2 className='mb-3 mt-3'>{item.courseId ? 'Edit Course' : 'Add new Course'}</h2>;

        return <div>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup className={`${this.errorClass(this.state.formErrors.name)}`}>
                        <Label for="name">Name</Label>
                        <Input type="text" name="name" id="name" value={item.name || ''}
                               onChange={this.handleChange} placeholder="Course name"/>
                    </FormGroup>
                    <FormGroup className={`${this.errorClass(this.state.formErrors.description)}`}>
                        <Label for="description">Description</Label>
                        <Input type="text" name="description" id="description" value={item.description || ''}
                               onChange={this.handleChange} placeholder="Course Description"/>
                    </FormGroup>
                    <div className="panel panel-default">
                        <FormErrors formErrors={this.state.formErrors} />
                    </div>
                    <FormGroup >
                        <Button color="primary" type="submit" disabled = {!this.state.formValid}>Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/admin/courselist">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }
}
export default withRouter(EditCourse);