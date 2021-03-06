import React, { Component } from 'react';
import { Button, ButtonGroup, Table, Container } from 'reactstrap';
import { Link } from 'react-router-dom';


class EnrollLecturer extends Component {
    defaultLecturer = {
        lecturerId: 0,
        name: '',
        username: '',
        password: '',
        email: '',
        active: true,
    };

    constructor(props) {
        super(props);

        this.state = { availableCourses: [], lecturer: this.defaultLecturer };
        this.add = this.add.bind(this);
    }

    async componentDidMount() {
        fetch(`/admin/lecturer/course/${this.props.match.params.id}/new`)
            .then(response => response.json())
            .then(data => this.setState({ availableCourses: data }));

        if (this.state.availableCourses !== null) {
            const currentLecturer = await (await fetch(`/admin/lecturer/${this.props.match.params.id}`)).json();
            this.setState({ lecturer: currentLecturer });
        }
    }

    async add(id) {
        await fetch(`/admin/lecturer/course/${this.state.lecturer.username}/${id}`, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
        })
            .then(response => response.json())
            .then(data => this.setState({ availableCourses: data }));
    };


    render() {
        const { availableCourses, isLoading } = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }



        const availableCourseList = availableCourses.map(CourseDetail => {
            return (
                <tr key={CourseDetail.id}>
                    <td>
                        {CourseDetail.course.name}
                    </td>
                    <td>
                        {CourseDetail.startDate}
                    </td>
                    <td>
                        {CourseDetail.endDate}
                    </td>
                    <td>
                        {CourseDetail.size}/{CourseDetail.maxSize}
                    </td>
                    
                    <td>
                        <ButtonGroup>
                            <Button outline size="sm" color="primary" onClick={() => this.add(CourseDetail.id)}>Add Lecturer</Button>
                        </ButtonGroup>
                    </td>
                </tr>
            );
        });

        return (
            <Container className='mt-5'>
                <h3><Link to={"/admin/lecturer/course/" + this.state.lecturer.lecturerId}><span className="fa fa-arrow-circle-left" aria-hidden="true"></span></Link> Available Courses for {this.state.lecturer.name}</h3>
                <table className='table table-hover table-striped text-center mt-3'>
                        <thead className='table-light'>
                        <tr>
                            <th>
                                Course Name
                            </th>
                            <th>
                                Course Start Date
                            </th>
                            <th>
                                Course End Date
                            </th>
                            <th>
                                Current Enrolment
                            </th>
                            <th>
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        {availableCourseList}
                    </tbody>
                </table>
            </Container>
        );

    }


}

export default EnrollLecturer;