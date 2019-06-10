import "./Presence.css";
import React, { Component } from "react";
import ReactTable from "react-table";
import "react-table/react-table.css";
import link from "./Root.js";
import {
  HelpBlock,
  FormGroup,
  FormControl,
  ControlLabel,
  Button
} from "react-bootstrap";
import LoaderButton from "../components/LoaderButton";
import Axios from "axios";
import Select from "react-select";
import "./NewSubject.css";
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
      id: "",
      data: []
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
    Axios.get("http://" + link + "/WS/home/subject/classes?id=" + id, {
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
      let id = this.state.selectedOption.value;
      console.log("Id:", id);
      const response = Axios.get(
        "http://" + link + "/WS/home/subject/absence-list?id=" + id,
        //"http://demo7358603.mockable.io/login",
        { responseType: "json" }
      ).then(response => {
        this.setState({
          data: response.data
        });
        console.log(response.data);
        const object = JSON.stringify(response.data);
        JSON.parse(object, (key, value) => {});
      });
      // const data = JSON.parse(response);
      console.log(response);
    } catch (err) {
      console.log(err);
    }
    //event.preventDefault();
  }

  render() {
    if (this.props.userStates.token.length === 0) {
      return <Redirect to="/login" />;
    }

    const columns = [
      {
        Header: "Név",
        accessor: "name" // String-based value accessors!
      },
      {
        Header: "Neptun kód",
        accessor: "neptun"
      },
      {
        Header: "Részvétel",
        accessor: "attended"
        // style: {
        //   background: rowInfo.row.name === "HIÁNYZOTT" ? "red" : ""
        //  }
      }
    ];

    return (
      <div className="NewSubject">
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
                  this.handleSubmit();
                }}
              >
                Tantárgyak listázása
              </Button>
            </div>
          </div>
        </div>
        <div className="col-md-8">
          <ReactTable data={this.state.data} columns={columns} />
        </div>
      </div>
    );
  }
}
