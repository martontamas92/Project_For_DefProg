import React, { Component } from "react";
import { Button, FormGroup, FormControl, ControlLabel } from "react-bootstrap";
import "./Login.css";
import Axios from "axios";
import qs from "qs";
import { Redirect } from "react-router";
import link from "./Root.js";

export default class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      uname: "",
      passwd: "",
      status: "demonstrator"
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  validateForm() {
    return this.state.uname.length > 0 && this.state.passwd.length > 0;
  }

  handleChange = event => {
    this.setState({
      [event.target.id]: event.target.value
    });
  };

  handleSubmit(event) {
    event.preventDefault();
    const headers = {
      Authorization: "Bearer",
      "Content-Type": "application/x-www-form-urlencoded"
    };
    const requestBody = qs.stringify({
      uname: this.state.uname,
      passwd: this.state.passwd,
      status: this.state.status
    });
    const { uname, passwd, status } = this.state;
    try {
      const response = Axios.post(
        "http://" + link + "/WS/home/authentication/login",
        requestBody,
        { headers: headers },
        { responseType: "json" }
      ).then(response => {
        this.props.updateUserStates(response.data);
        console.log(response.headers);
      });
    } catch (err) {
      console.log(err);
    }
  }
  render() {
    if (this.props.userStates.token.length > 0) {
      return <Redirect to="/newsubject" />;
    }

    return (
      <div className="Login">
        <form onSubmit={this.handleSubmit}>
          <FormGroup controlId="uname" bsSize="large">
            <ControlLabel>Email cím</ControlLabel>
            <FormControl
              autoFocus
              type="email"
              value={this.state.uname}
              onChange={this.handleChange}
            />
          </FormGroup>
          <FormGroup controlId="passwd" bsSize="large">
            <ControlLabel>Jelszó</ControlLabel>
            <FormControl
              value={this.state.passwd}
              onChange={this.handleChange}
              type="password"
            />
          </FormGroup>
          <Button
            block
            bsSize="large"
            disabled={!this.validateForm()}
            type="submit"
          >
            Bejelentkezés
          </Button>
        </form>
      </div>
    );
  }
}
