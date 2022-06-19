import React, { Component } from 'react';
import { Button, ButtonGroup, Table , Container} from 'reactstrap';
import { Link } from 'react-router-dom';


class Lecturer extends Component {
    constructor(props) {
        super(props);

        this.state = {lecturers: []};
        this.remove = this.remove.bind(this);
    }

    async componentDidMount() {
        fetch('/admin/lecturer')
            .then(response => response.json())
            .then(data => this.setState({lecturers: data}));
      }

      async remove(id) {
        await fetch(`/admin/lecturer/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        })
        .then(response => response.json())
        .then(data => this.setState({lecturers: data}));
    };
    
      
    render() {
        const {lecturers, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const lecturerList = lecturers.map(lecturer => {
            return (
                <tr key= {lecturer.lecturerId}>
                    <td>
                        {lecturer.lecturerId}
                    </td>
                    <td>
                        {lecturer.name}
                    </td>
                    <td>
                        {lecturer.username}
                    </td>
                    <td>
                        {lecturer.password}
                    </td>
                    <td>
                        {lecturer.email}
                    </td>
                    <td>
                        {String(lecturer.active)}
                    </td>
                    <td>
                        <ButtonGroup>
                            <Button size="sm" color="primary" tag={Link} to={"/admin/lecturer/" + lecturer.lecturerId}>Edit</Button>
                            <Button size="sm" color="secondary" tag={Link} to={"/admin/lecturer/course/" + lecturer.lecturerId}>Courses Taught</Button>
                            <Button size="sm" color="danger" onClick={() => this.remove(lecturer.lecturerId)}>Delete</Button>
                        </ButtonGroup>
                    </td>
                </tr>
            );
        });

        return (
            <Container className='mt-5'>
                <div className="float-end">
                    <Button color="success" tag={Link} to="/admin/lecturer/new">Add Lecturer</Button>
                </div>
                <h3>Lecturers</h3>
                <Table>
                    <thead>
                        <tr>
                            <th>
                                lecturer ID
                            </th>
                            <th>
                                lecturer Name
                            </th>
                            <th>
                                lecturer Username
                            </th>
                            <th>
                                lecturer Password
                            </th>
                            <th>
                                lecturer Email 
                            </th>
                            <th>
                                Currently Active
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        {lecturerList}
                    </tbody>
                </Table>
            </Container>
        );

    }
    

}

export default Lecturer;