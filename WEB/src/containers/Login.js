import React, { Component } from "react";
import { Button, FormGroup, FormControl, ControlLabel } from "react-bootstrap";
import "./Login.css";
import Axios from "axios";
import { Redirect } from "react-router";

export default class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      email: "",
      password: ""
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  validateForm() {
    return this.state.email.length > 0 && this.state.password.length > 0;
  }

  handleChange = event => {
    this.setState({
      [event.target.id]: event.target.value
    });
  };

  handleSubmit(event) {
    event.preventDefault();

    const { email, password } = this.state;
    try {
      const response = Axios.post(
        "http://demo7358603.mockable.io/login",
        {
          email,
          password
        },
        { responseType: "json" }
      ).then(response => {
        this.props.updateUserStates(response.data);
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
          <FormGroup controlId="email" bsSize="large">
            <ControlLabel>Email cím</ControlLabel>
            <FormControl
              autoFocus
              type="email"
              value={this.state.email}
              onChange={this.handleChange}
            />
          </FormGroup>
          <FormGroup controlId="password" bsSize="large">
            <ControlLabel>Jelszó</ControlLabel>
            <FormControl
              value={this.state.password}
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
