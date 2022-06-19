import React, { Component } from 'react';
import {Button} from 'reactstrap';
import { Link } from 'react-router-dom';

class Home extends Component {


    render(){
        return (
            <div>
                <Button size="sm" color="primary" tag={Link} to={"/admin/student"}>Go to Student Page</Button>
                <Button size="sm" color="secondary" tag={Link} to={"/admin/lecturer"}>Go to Lecturer Page</Button>
            </div>

        )
    }


}
export default Home;