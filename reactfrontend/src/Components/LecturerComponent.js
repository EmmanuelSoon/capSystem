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
                        *****
                    </td>
                    <td>
                        {lecturer.email}
                    </td>
                    <td>
                        {String(lecturer.active)}
                    </td>
                    <td>
                        <ButtonGroup>
                            <Button size="sm" color="primary" tag={Link} to={"/admin/lecturer/" + lecturer.lecturerId}> Edit Info <span className="fa fa-pencil"></span></Button>
                            <Button size="sm" color="secondary" tag={Link} to={"/admin/lecturer/course/" + lecturer.lecturerId}>Courses Taught <span className="fa fa-info"></span></Button>
                            <Button size="sm" color="danger" onClick={() => this.remove(lecturer.lecturerId)}>Delete <span className="fa fa-times"></span></Button>
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
                <table className='table table-hover text-center mt-3'>
                    <thead className='table-light'>
                        <tr>
                            <th>
                                Lecturer ID
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
                        {lecturerList}
                    </tbody>
                </table>
            </Container>
        );

    }
    

}

export default Lecturer;