import React from "react";
import ReactTable from '../../node_modules/react-table';
import matchSorter from '../../node_modules/match-sorter';

class Model extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          columns:[
            {
              Header: "Model",
              columns: [
                {
                  Header: 'Id',
                  accessor: 'modelId',
                  maxWidth: 40
                },
                {
                  Header: "Name",
                  id: "name",
                  accessor: d => d.name,
                  filterMethod: (filter, rows) =>
                  matchSorter(rows, filter.value, { keys: ["name"] }),
                  filterAll: true
                }
              ]
            }
          ]
          }
    }
      render() {
        return (
            <div className="container" style={{ marginTop: 15 }}>
                <button onClick={()=>{this.props.handleClick()}}>Load Data</button>
                <p/>
                {this.props.models.length > 0 ? 
                <ReactTable
                  data={this.props.models}
                  filterable
                  defaultFilterMethod={(filter, row) =>
                  String(row[filter.id]) === filter.value}
                  columns={this.state.columns}
                  defaultPageSize = {5}
                  pageSizeOptions = {[5, 10, 15, 20]}
                  className="-striped -highlight"
                /> : null}
            </div>
        );
      }
}

export default Model;