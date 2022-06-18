import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';

class EditStudent extends Component {

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
            item: this.emptyItem
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }


    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            const student = await (await fetch(`/admin/student/${this.props.match.params.id}`)).json();
            student.active = true;
            this.setState({item: student});
        }
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;
    
        await fetch('/admin/student' + (item.studentId ? '/' + item.studentId : ''), {
            method: (item.studentId) ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        });
        this.props.history.push('/admin/student');
    }

    render() {
        const {item} = this.state;
        const title = <h2>{item.studentId ? 'Edit Student' : 'Add Student'}</h2>;
    
        return <div>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="name">Name</Label>
                        <Input type="text" name="name" id="name" value={item.name || ''}
                               onChange={this.handleChange} autoComplete="name"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="username">Username</Label>
                        <Input type="text" name="username" id="username" value={item.username || ''}
                               onChange={this.handleChange} autoComplete="username"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="password">Password</Label>
                        <Input type="text" name="password" id="password" value={item.password || ''}
                               onChange={this.handleChange} autoComplete="password"/>
                    </FormGroup>
                    <FormGroup>
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
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/admin/student">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }


}
export default withRouter(EditStudent);