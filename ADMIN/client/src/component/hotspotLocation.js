import React from "react";
import ReactTable from '../../node_modules/react-table';
import matchSorter from '../../node_modules/match-sorter';

class HotspotLocation extends React.Component {
      constructor(props) {
        super(props);
        this.state = {
            carIdSelected: 0,
            carImageIdSelected: 0,
            displayHotspotLocations: false
            //cars: [],
            //test: [],
          //   columns:[
          //   {
          //       Header: "Ids",
          //       columns: [
          //         {
          //           Header: 'Car',
          //           id: 'carId',
          //           maxWidth: 100,
          //           accessor: d => d.carId
          //         },
          //         {
          //           Header: 'Hotspot',
          //           id: "hotspotId",
          //           maxWidth: 100,
          //           accessor: d => d.carImage.map((val) => val.hotspotLocations.map((val2) => {return val2.hotspotId}))
          //         }
          //       ]
          //   },
          //   {  
          //     Header: "Hotspot Locations",
          //     columns: [
          //       {
          //         Header: 'Id',
          //         id: 'htsptId',
          //         maxWidth: 40,
          //         accessor: d => d.carImage.map((val) => val.hotspotLocations.map((val2) => {return val2.hotspotId}))
          //       },
          //       {
          //         Header: "X",
          //         id: "xLoc",
          //         maxWidth: 50,
          //         accessor: d => d.carImage.map((val) => val.hotspotLocations.map((val2) => {return val2.xLoc}))
          //       },
          //       {
          //         Header: "Y",
          //         id: "yLoc",
          //         maxWidth: 50,
          //         accessor: d => d.carImage.map((val) => val.hotspotLocations.map((val2) => {return val2.yLoc}))
          //       },
          //       {
          //         Header: "Description",
          //         id: "hotspotDesc",
          //         accessor: d => d.carImage.map((val) => val.hotspotLocations.map((val2) => {return val2.hotspotDesc}))
          //       },
          //       {
          //         Header: "Active",
          //         id: "active",
          //         maxWidth: 80,
          //         accessor: d => d.carImage.map((val) => val.hotspotLocations.map((val2) => {return val2.active.toString()}))
          //       }
          //     ]
          //   }
          // ]
        }
    }
      render() {
        //const locationData = this.props.hotspotLocations;
        // return (
        //     <div className="container" style={{ marginTop: 50 }}>
        //         <button onClick={()=>{this.props.handleClick()}}>Load Data</button>
        //         <p/>
        //         {this.props.hotspotLocations.length > 0 ? 
        //         <ReactTable
        //         data={this.props.hotspotLocations}
        //         columns={this.state.columns}
        //         pivotBy={["carId", "hotspotId"]}
        //         filterable
        //         defaultPageSize = {5}
        //         pageSizeOptions = {[5, 10, 15, 20]}
        //         className="-striped -highlight"
        //         /> : null}
        //     </div>
        // );
        // const { data } = this.props.hotspotLocations;
        if (this.props.carsHotspotLocations.length === 0 || this.props.hotspotLocations.length === 0) { 
          return (
            <div className="container" style={{ marginTop: 15 }}>
          <button onClick={()=>{this.props.handleClick()}}>Load Data</button>
          <p/>
          {this.props.carsHotspotLocations.length > 0 ? 
          <ReactTable
          data={this.props.carsHotspotLocations}
          columns={[
            {
              Header: "Car Information",
              columns: [
                {
                  Header: "Select",
                  id: "radio",
                  accessor: "",
                  maxWidth: 60,
                  Cell: d => {
                    return (
                      <input
                        type = "radio"
                        name = "carId"
                        checked={this.state.carIdSelected === d.value.carId}
                        onClick={() => this.props.handleRadioBtnCarId(d.value.carId)}
                        //onClick={() => {this.setState({testData: d})}}
                        onChange={() => {this.setState({carIdSelected: d.value.carId})}}
                      />
                    );
                    }
                },
                {
                  Header: 'Id',
                  id: "carId",
                  maxWidth: 60,
                  accessor: d => d.carId
                },
                {
                  Header: 'Make',
                  id: "mnfctrNm",
                  accessor: d => d.manufacturer.name,
                  filterMethod: (filter, rows) =>
                  matchSorter(rows, filter.value, { keys: ["mnfctrNm"] }),
                  filterAll: true
                },
                {
                  Header: 'Model',
                  id: "mdlNm",
                  accessor: d => d.model.name,
                  filterMethod: (filter, rows) =>
                  matchSorter(rows, filter.value, { keys: ["mdlNm"] }),
                  filterAll: true
                },
                {
                  Header: 'Year',
                  id: "yr",
                  accessor: d => d.modelYear.yearValue ,
                  filterMethod: (filter, rows) =>
                  matchSorter(rows, filter.value, { keys: ["yr"] }),
                  filterAll: true
                },
                {
                  Header: "Active",
                  id: "actv",
                  // maxWidth: 80,
                  accessor: d => d.active.toString(),
                  filterMethod: (filter, rows) =>
                  matchSorter(rows, filter.value, { keys: ["actv"] }),
                  filterAll: true
                }
              ]
            }
          ]}
            defaultPageSize={5}
            className="-striped -highlight"
            filterable
          />
          : null}
          
          </div>
          )
          
        } else {
          return (
            <div className="container" style={{ marginTop: 15 }}>
          <button onClick={()=>{this.props.handleClick()}}>Load Data</button>
          <p/>
          {this.props.carsHotspotLocations.length > 0 ? 
          <ReactTable
          data={this.props.carsHotspotLocations}
          columns={[
            {
              Header: "Car Information",
              columns: [
                {
                  Header: "Select",
                  id: "radio",
                  accessor: "",
                  maxWidth: 60,
                  Cell: d => {
                    return (
                      <input
                        type = "radio"
                        name = "carId"
                        checked={this.state.carIdSelected === d.value.carId}
                        onClick={() => this.props.handleRadioBtnCarId(d.value.carId)}
                        onChange={() => {this.setState({carIdSelected: d.value.carId})}}
                      />
                    );
                    }
                },
                {
                  Header: 'Id',
                  id: "carId",
                  maxWidth: 60,
                  accessor: d => d.carId
                },
                {
                  Header: 'Make',
                  id: "mnfctrNm",
                  accessor: d => d.manufacturer.name,
                  filterMethod: (filter, rows) =>
                  matchSorter(rows, filter.value, { keys: ["mnfctrNm"] }),
                  filterAll: true
                },
                {
                  Header: 'Model',
                  id: "mdlNm",
                  accessor: d => d.model.name,
                  filterMethod: (filter, rows) =>
                  matchSorter(rows, filter.value, { keys: ["mdlNm"] }),
                  filterAll: true
                },
                {
                  Header: 'Year',
                  id: "yr",
                  accessor: d => d.modelYear.yearValue ,
                  filterMethod: (filter, rows) =>
                  matchSorter(rows, filter.value, { keys: ["yr"] }),
                  filterAll: true
                },
                {
                  Header: "Active",
                  id: "actv",
                  // maxWidth: 80,
                  accessor: d => d.active.toString(),
                  filterMethod: (filter, rows) =>
                  matchSorter(rows, filter.value, { keys: ["actv"] }),
                  filterAll: true
                }
              ]
            }
          ]}
            defaultPageSize={5}
            className="-striped -highlight"
            filterable
          />
          : null}
              {console.log("this.props.hotspotLocations")}
              {console.log(this.props.hotspotLocations.carImage)}
              <p/><p/>
            {<ReactTable
            data={this.props.hotspotLocations.carImage}
            columns={[
              {
                Header: "Car Image Information",
                columns: [
                  {
                    Header: "Select",
                    id: "radio",
                    accessor: "",
                    maxWidth: 60,
                    Cell: d => {
                      return (
                        <input
                          type = "radio"
                          name = "carImageId"
                          checked={this.state.carImageIdSelected === d.value.carImageId}
                          // onClick={() => {this.setState({carImageIdSelected: d.value.carImageId})}}
                          onClick={() => this.props.handleRadioBtnCarImageId(d.value.carImageId)}
                          onChange={() => {this.setState({carImageIdSelected: d.value.carImageId})}}
                        />
                      );
                      }
                  },
                  {
                    Header: "Id",
                    id: "carImgId",
                    maxWidth: 60,
                    accessor: d => d.carImageId,
                  },
                  {
                    Header: "URL",
                    id: "uri",
                    accessor: d => d.uri
                  },
                  {
                    Header: "Exterior Image",
                    id: "exteriorImage",
                    maxWidth: 110,
                    accessor: d => d.exteriorImage.toString()
                  },
                  {
                    Header: "Active",
                    id: "active",
                    maxWidth: 80,
                    accessor: d => d.active.toString()
                  }
                ]
              }
            ]}
            defaultPageSize={5}
            className="-striped -highlight"
            //pivotBy={["carId", "carImgId", "hotspotId"]}
            //pivotBy={["carImgId", "hotId"]}
            filterable
            
          />}
          {console.log("this.props.hotspotLocationsCarImages")}
              {console.log(this.props.hotspotLocationsCarImages)}
              {console.log("this.props.hotspotLocationsCarImages.hotspotLocations")}
              {console.log(this.props.hotspotLocationsCarImages.hotspotLocations)}
              <p/><p/>
            <ReactTable
            data={this.props.hotspotLocationsCarImages.hotspotLocations}
            columns={[
              {
                Header: "Hotspot Location Information",
                columns: [
                  {
                    Header: "Id",
                    id: "hotspotId",
                    maxWidth: 60,
                    accessor: d => d.hotspotId
                  },
                  {
                    Header: "X",
                    id: "xLoc",
                    maxWidth: 50,
                    accessor: d => d.xLoc
                  },
                {
                  Header: "Y",
                  id: "yLoc",
                  maxWidth: 50,
                  accessor: d => d.yLoc
                },
                {
                  Header: "Description",
                  id: "hotspotDesc",
                  accessor: d => d.hotspotDesc
                },
                {
                  Header: "Active",
                  id: "active",
                  maxWidth: 80,
                  accessor: d => d.active.toString()
                }
                ]
              }
            ]}
            defaultPageSize={5}
            className="-striped -highlight"
            filterable
            />
          </div>
          )
        }
      }
}

export default HotspotLocation;