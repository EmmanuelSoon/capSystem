import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import { FormErrors } from './FormErrors';


class NewBatch extends Component {

    emptyItem = {
        startDate: '',
        endDate: '',
        size: '',
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem,
            startValid: false,
            endValid: false,
            sizeValid: false,
            formValid: false,
            formErrors: {
                startDate:'',
                endDate:'',
                size:''
            }
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        this.setState({formValid: true});
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
        let startValid = this.state.startValid;
        let endValid = this.state.endValid;
        let sizeValid = this.state.sizeValid;
        let now = new Date();
        let start = this.state.item.startDate ;
        let end = this.state.item.endDate;
        switch(fieldName) {
            case 'startDate':
                startValid = (value) =>  new Date(value) >= now;
                fieldValidationErrors.startDate = startValid ? '' : ' start Date must later than ' + now.toString();
                break;
            case 'endDate':
                if (!startValid) {
                    fieldValidationErrors.endDate = 'please comply startDate first!';
                }
                else {
                    endValid = (value) => end.length > 0 && new Date(value) > now && new Date(value) > new Date(start);
                    fieldValidationErrors.startDate = endValid ? '' : ' end Date must later than start date';
                }
                console.log(value);
                break;
            case 'size':
                sizeValid = value > 0;
                fieldValidationErrors.size = sizeValid ? '' : 'The maximum size must be a positive number';
                console.log(value);
                break;
            default:
                break;
        }
        this.setState({formErrors: fieldValidationErrors,
            startValid: startValid,
            endValid: endValid,
            sizeValid: sizeValid,
        }, this.validateForm);
    }

    validateForm() {
        this.setState({formValid: this.state.startValid && this.state.endValid && this.state.sizeValid});
    }

    errorClass(error) {
        return(error.length === 0 ? '' : 'has-error');
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;
        await fetch('/admin/course/batch/' + this.props.match.params.id, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item)
        });
        this.props.history.push('/admin/coursedetails/' + this.props.match.params.id)
    }


    render() {
        const {item} = this.state;
        const title = <h2 className='mb-3 mt-3'>{'Add new Batch'}</h2>;

        return <div>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup className={`${this.errorClass(this.state.formErrors.startDate)}`}>
                        <Label for="startDate">Start Date</Label>
                        <Input type="date" name="startDate" id="startDate" value={item.startDate || ''}
                               onChange={this.handleChange}/>
                    </FormGroup>
                    <FormGroup className={`${this.errorClass(this.state.formErrors.endDate)}`}>
                        <Label for="endDate">End Date</Label>
                        <Input type="date" name="endDate" id="endDate" value={item.endDate || ''}
                               onChange={this.handleChange} />
                    </FormGroup>
                    <FormGroup className={`${this.errorClass(this.state.formErrors.size)}`}>
                        <Label for="size">Maximum Size</Label>
                        <Input type="number" name="size" id="size" value={item.size || ''}
                               onChange={this.handleChange} />
                    </FormGroup>
                    <div className="panel panel-default">
                        <FormErrors formErrors={this.state.formErrors} />
                    </div>
                    <FormGroup >
                        <Button color="primary" type="submit" disabled = {!this.state.formValid}>Save</Button>{' '}
                        <Button color="secondary" tag={Link} to={'/admin/coursedetails/'+this.props.match.params.id}>Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }
}
export default withRouter(NewBatch);