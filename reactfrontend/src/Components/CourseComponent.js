import React, { Component } from 'react';
import { Button, ButtonGroup, Table , Container} from 'reactstrap';
import { Link } from 'react-router-dom';

class Course extends Component {
    constructor(props) {
        super(props);
        this.state = {
            courses : []
        }

    }

    async componentDidMount() {
        fetch('/admin/course')
            .then(response => response.json())
            .then(data => this.setState({courses: data}));
    }

    render() {
        const {courses, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }
        const CourseList = courses.map(course => {
            return (
                <tr>
                    <td>{course.courseId}</td>
                    <td>{course.name}</td>
                    <td>{course.description}</td>
                    <td>
                    <ButtonGroup>
                        <Button color="primary" tag={Link} to={"/admin/coursedetails/" +course.courseId}>View Batches</Button>
                        <Button color="danger">Delete Course</Button>
                    </ButtonGroup></td>
                </tr>
            );
        });

        return(
            <Container>
                <div>
                    <h2>Course List</h2>
                </div>
            <Table>
                <thead>
                <tr>
                    <th>Course ID</th>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Action</th>
                </tr></thead>
                <tbody>
                {CourseList}
                </tbody>
            </Table>
            </Container>
        );
    }
}
export default Course;