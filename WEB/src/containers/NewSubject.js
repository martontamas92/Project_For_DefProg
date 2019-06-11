import React, { Component } from "react";
import link from "./Root.js";
import {
  HelpBlock,
  FormGroup,
  FormControl,
  ControlLabel,
  Button
} from "react-bootstrap";
import LoaderButton from "../components/LoaderButton";
import "./Signup.css";
import Axios from "axios";
import Select from "react-select";
import "./NewSubject.css";
import QRCode from "qrcode.react";
import { Redirect } from "react-router";

const customStyles = {
  option: (provided, state) => ({
    ...provided,
    borderBottom: "1px dotted pink",
    color: state.isSelected ? "red" : "blue",
    padding: 20
  })
};

export default class NewSubject extends Component {
  constructor(props) {
    super(props);
    this.state = {
      email: "",
      password: "",
      uname: "",
      subname: "",
      selectedOption: null,
      options: [],
      qrValue: "",
      subjectmajor: "",
      id: ""
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  componentDidMount = () => {
    this.getSubjectList();
  };

  getSubjectList = () => {
    let { id } = this.state;
    id = this.props.userStates.demonstrator.id;
    Axios.get("http://" + link + "/WS/home/demonstrator/subjectList?id=" + id, {
      headers: {
        Authorization: "Bearer " + this.props.userStates.token
      }
    }).then(response => {
      this.setState({
        options: response.data
      });
    });
  };

  handleChangeSubject = selectedOption => {
    this.setState({ selectedOption });
    console.log(`Option selected:`, selectedOption);
  };

  validateForm() {
    return this.state.subname.length > 0;
  }

  handleChange = event => {
    this.setState({
      [event.target.id]: event.target.value
    });
  };

  handleSubmit(event) {
    //const { email, password, middlename, firstname, lastname } = this.state;
    try {
      const response = Axios.post(
        "http://" + link + "/WS/home/demonstrator/create-subject",
        //"http://demo7358603.mockable.io/login",
        {
          subjectName: this.state.subname,
          subjectMajor: this.state.subjectmajor,
          demonstrator: this.props.userStates.demonstrator
        },

        { responseType: "json" },
        {
          headers: {
            Authorization: "Bearer " + this.props.userStates.token
          }
        }
      ).then(response => {
        this.getSubjectList();
        console.log(response.data);
        const object = JSON.stringify(response.data);
        JSON.parse(object, (key, value) => {
          alert(value);
        });
      });
      // const data = JSON.parse(response);
      console.log(response);
    } catch (err) {
      console.log(err);
    }
    event.preventDefault();
  }

  getQR = () => {
    const selectedFruit = this.selectedOption;
    Axios.post(
      "http://" + link + "/WS/home/demonstrator/create-lecture",
      // {
      // headers: {
      //   Authorization: "Bearer " + this.props.userStates.token
      // }
      // },
      {
        subjectName: this.state.selectedOption.value,
        demonstrator: {
          id: this.props.userStates.demonstrator.id,
          name: {
            firstName: this.props.userStates.demonstrator.name.firstName,
            lastName: this.props.userStates.demonstrator.name.lastName
          }
        }
      },
      {
        responseType: "json",
        headers: {
          Authorization: "Bearer " + this.props.userStates.token
        }
      }
    )
      .then(response => {
        this.setState({
          qrValue: response.data
        });
      })
      .catch(err => {
        //todo redirect to login
      });
  };

  QRRender = () => {
    if (this.state.qrValue.length > 0) {
      return <QRCode value={this.state.qrValue} size="512" />;
    }
    return "";
  };

  render() {
    if (this.props.userStates.token.length === 0) {
      return <Redirect to="/login" />;
    }

    return (
      <div className="NewSubject">
        <div className="row" />
        <div className="col-md-4">
          <div className="panel panel-default">
            <div className="panel-heading">Tantárgy hozzáadás</div>
            <div className="panel-body">
              <form onSubmit={this.handleSubmit}>
                <FormGroup controlId="subname" bsSize="large">
                  <ControlLabel>Tantárgy neve</ControlLabel>
                  <FormControl
                    autoFocus
                    type="text"
                    value={this.state.subname}
                    onChange={this.handleChange}
                  />
                </FormGroup>
                <FormGroup controlId="subjectmajor" bsSize="large">
                  <ControlLabel>Szak neve</ControlLabel>
                  <FormControl
                    autoFocus
                    type="text"
                    value={this.state.subjectmajor}
                    onChange={this.handleChange}
                  />
                </FormGroup>
                <Button
                  block
                  bsSize="large"
                  disabled={!this.validateForm()}
                  type="submit"
                >
                  Tantárgy regisztrálása
                </Button>
              </form>
            </div>
          </div>
        </div>
        <div className="col-md-4">
          <div className="panel panel-default">
            <div className="panel-heading">Tantárgyak listázása</div>
            <div className="panel-body">
              <FormGroup>
                <ControlLabel>Tantárgy neve</ControlLabel>
                <Select
                  value={this.selectedOption}
                  onChange={this.handleChangeSubject}
                  options={this.state.options}
                  styles={customStyles}
                />
              </FormGroup>
              <Button
                block
                bsSize="large"
                onClick={() => {
                  this.getQR();
                }}
              >
                QR kod lekérdezése
              </Button>
            </div>
          </div>
        </div>
        <div className="row">
          <div className="col-md-4 col-md-offset-4">{this.QRRender()}</div>
        </div>
      </div>
    );
  }
}
