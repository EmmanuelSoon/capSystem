import React, {Component} from 'react';
import {Navbar, NavbarBrand, Nav,NavbarToggler, Collapse, NavItem} from 'reactstrap';
import { NavLink } from 'react-router-dom';

import logo from '../assets/images/logo.jpg'

class Header extends Component {

    constructor(props){
        super(props);
        this.state = { 
            isNavOpen: false
        };
        this.toggleNav = this.toggleNav.bind(this);
    }

    toggleNav(){
        this.setState({ //swapping the value, if it is false, change to true
            isNavOpen: !this.state.isNavOpen
        });
    }

    render(){
        return ( //short form for react fragment, <div> will have a node in the dom but fragment dont

            <React.Fragment> 
                <Navbar dark expand="md"> 
                    <div className = "container">
                        <NavbarToggler onClick={this.toggleNav} />
                        <NavbarBrand className="mr-auto" href = "/">
                            <img src={logo} height="40" width="41"
                                alt="blank" /> Cap System
                        </NavbarBrand>
                        <Collapse isOpen={this.state.isNavOpen} navbar>
                            <Nav navbar>
                                <NavItem>
                                    <NavLink className="nav-link" to="/admin" exact={true}>
                                        Dashboard
                                    </NavLink>
                                </NavItem>
                                <NavItem>
                                    <NavLink className="nav-link" to="/admin/student">
                                        View Students
                                    </NavLink>
                                </NavItem>
                                <NavItem>
                                    <NavLink className="nav-link" to="/admin/courselist">
                                        View Courses
                                    </NavLink>
                                </NavItem>
                                <NavItem>
                                    <NavLink className="nav-link" to="/admin/lecturer">
                                        View Lecturers
                                    </NavLink>
                                </NavItem>

                            </Nav>
                        </Collapse>    
                    </div>
                </Navbar>

            </React.Fragment>
        );
    }
}

export default Header;