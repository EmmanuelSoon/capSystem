import React, { Component } from 'react';
import { Button, ButtonGroup, Table , Container} from 'reactstrap';
import { Link } from 'react-router-dom';


class LecturerCourse extends Component {
    defaultLecturer = {
        name: '',
        username: '',
        password: '',
        email: '',
        active: true,
    };

    constructor(props) {
        super(props);

        this.state = {lecturerCourses: [], lecturer: this.defaultLecturer};
        this.remove = this.remove.bind(this);
    }

    async componentDidMount() {
        fetch(`/admin/lecturer/course/${this.props.match.params.id}`)
            .then(response => response.json())
            .then(data => this.setState({lecturerCourses: data}));

        if(this.state.lecturerCourses !== null){
            const currentLecturer = await (await fetch(`/admin/lecturer/${this.props.match.params.id}`)).json();
            this.setState({lecturer: currentLecturer});
        }
      }

      async remove(id) {
        await fetch(`/admin/lecturer/course/${this.state.lecturer.username}/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        })
        .then(response => response.json())
        .then(data => this.setState({lecturerCourses: data}));
    };
    
      
    render() {
        const {lecturerCourses, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }



        const lecturerCourseList = lecturerCourses.map(lecturerCourseDetail => {
            return (
                <tr key= {lecturerCourseDetail.courseDetailId}>
                    <td>
                        {lecturerCourseDetail.courseName}
                    </td>
                    <td>
                        {lecturerCourseDetail.startDate}
                    </td>
                    <td>
                        {lecturerCourseDetail.endDate}
                    </td>


                    <td>
                        <ButtonGroup>
                            {/* <Button size="sm" color="primary" tag={Link} to={"/admin/student/course" + studentCourseDetail.studentId}>Edit</Button> */}
                            <Button size="sm" color="danger" onClick={() => this.remove(lecturerCourseDetail.courseDetailId)}>Delete</Button>
                        </ButtonGroup>
                    </td>
                </tr>
            );
        });

        return (
            <Container fluid>
                <div className="float-right">
                    {/* <Button color="success" tag={Link} to={`/admin/student/course/${this.state.student.username}/new`}>Enroll Student into a new Course</Button> */}
                </div>
                <h3>{this.state.lecturer.name}'s Courses</h3>
                <Table>
                    <thead>
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
                        </tr>
                    </thead>
                    <tbody>
                        {lecturerCourseList}
                    </tbody>
                </Table>
            </Container>
        );

    }
    

}

export default LecturerCourse;