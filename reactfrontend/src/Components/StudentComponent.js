import React, { Component } from 'react';
import { Button, ButtonGroup, Table , Container, Row} from 'reactstrap';
import { Link } from 'react-router-dom';


class Student extends Component {
    constructor(props) {
        super(props);

        this.state = {students: []};
        this.remove = this.remove.bind(this);
    }

    async componentDidMount() {
        fetch('/admin/student')
            .then(response => response.json())
            .then(data => this.setState({students: data}));
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
    
      
    render() {
        const {students, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const studentList = students.map(student => {
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
                            <Button size="sm" color="primary" tag={Link} to={"/admin/student/" + student.studentId}>Edit</Button>
                            <Button size="sm" color="secondary" tag={Link} to={"/admin/student/course/" + student.studentId}>Enrolment</Button>
                            <Button size="sm" color="danger" onClick={() => this.remove(student.studentId)}>Delete</Button>
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
                                    student ID
                                </th>
                                <th>
                                    student Name
                                </th>
                                <th>
                                    student Username
                                </th>
                                <th>
                                    student Password
                                </th>
                                <th>
                                    student Email 
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
               
            </Container>
        );

    }
    

}

export default Student;