import React, { Component } from 'react';
import { Button, ButtonGroup, Table , Container} from 'reactstrap';
import { Link } from 'react-router-dom';

class Course extends Component {
    constructor(props) {
        super(props);
        this.state = {
            courses : [],
            isLoaded: false
        }
        this.deleteCourse = this.deleteCourse.bind(this);
    }

    async componentDidMount() {
        fetch('/admin/course')
            .then(response => response.json())
            .then(data => this.setState({courses: data, isLoaded: true}));
    }

    async deleteCourse(id) {
        await fetch(`/admin/course/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(
            this.setState({courses: this.state.courses.filter(course => course.courseId !== id)})
        );
    };


    render() {
        const {courses, isLoaded} = this.state;

        if (!isLoaded) {
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
                        <Button color="danger" onClick={() => this.deleteCourse(course.courseId)}>Delete Course</Button>
                        <Button color="success" tag={Link} to={"/admin/course/" +course.courseId}>Edit Course Info</Button>
                    </ButtonGroup></td>
                </tr>
            );
        });

        return(
            <Container className='mt-5'>
                <div className="float-end">
                    <Button color="success" tag={Link} to="/admin/course/new">Add Course</Button>
                </div>
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