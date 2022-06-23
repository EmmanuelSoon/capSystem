import React, { Component } from 'react';
import { Button, ButtonGroup, Table , Container, Row} from 'reactstrap';
import { Link } from 'react-router-dom';


class Student extends Component {
    constructor(props) {
        super(props);

        this.state = {
            students: [],
            min: 0,
            max: 10,
            isLoading: true,
        };
        this.remove = this.remove.bind(this);
        this.decreaseCount = this.decreaseCount.bind(this);
        this.increaseCount = this.increaseCount.bind(this);
    }

    async componentDidMount() {
        fetch('/admin/student')
            .then(response => response.json())
            .then(data => this.setState({students: data, isLoading: false}));
      }

      async remove(id) {
        await fetch(`/admin/student/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        })
        .then(response => response.json())
        .then(data => this.setState({students: data}));
    };
    
    increaseCount(){
        this.setState({max : this.state.max + 10, min: this.state.min + 10})
    }

    decreaseCount(){
        this.setState({max : this.state.max - 10, min: this.state.min - 10})
    }
      
    render() {
        const {students, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const studentList = students.filter((item, i) => i < this.state.max && i >= this.state.min).map(student => {
            return (
                <tr key= {student.studentId}>
                    <td>
                        {student.studentId}
                    </td>
                    <td>
                        {student.name}
                    </td>
                    <td>
                        {student.username}
                    </td>
                    <td>
                        *****
                    </td>
                    <td>
                        {student.email}
                    </td>
                    <td>
                        {String(student.active)}
                    </td>
                    <td>
                        <ButtonGroup>
                            <Button size="sm" color="primary" tag={Link} to={"/admin/student/" + student.studentId}>Edit info <span className="fa fa-pencil"></span></Button>
                            <Button size="sm" color="secondary" tag={Link} to={"/admin/student/course/" + student.studentId}> View Enrolment <span className="fa fa-info"></span></Button>
                            <Button size="sm" color="danger" onClick={() => this.remove(student.studentId)}>Delete <span className="fa fa-times"></span></Button>
                        </ButtonGroup>
                    </td>
                </tr>
            );
        });

        return (
            <Container className='mt-5'>
                <div className="float-end">
                    <Button color="success" tag={Link} to="/admin/student/new">Add Student</Button>
                </div>
                
                <h3>Students</h3>
                <table className='table table-hover text-center mt-3'>
                    <thead className='table-light'>
                        <tr>
                            <th>
                                Student ID
                            </th>
                            <th>
                                Name
                            </th>
                            <th>
                                Username
                            </th>
                            <th>
                                Password
                            </th>
                            <th>
                                Email 
                            </th>
                            <th>
                                Currently Active
                            </th>
                            <th>
                                
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        {studentList}
                    </tbody>
                </table>
                <div className='row'>
                    <div className='col-1' >
                        <Button color='success' outline className='rounded-circle' onClick={() => this.decreaseCount()} disabled={this.state.min <= 0}><i class="fa fa-arrow-left" aria-hidden="true"></i></Button> 
                    </div>
                    <div className='col-10 text-center lead fs-4'>
                        {this.state.min+1} ... {this.state.max}
                    </div>
                    <div className='col-1 float-end'>
                        <Button color='success' outline className='rounded-circle' onClick={() => this.increaseCount()}  disabled={this.state.max >= students.length}><i class="fa fa-arrow-right" aria-hidden="true"></i></Button>
                    </div>
                </div>
            </Container>
        );

    }
    

}

export default Student;