 const navigate_link =  document.getElementsByClassName("navigate_link") //
 const navigate_button =  document.getElementsByClassName("navigate_button")


    let part1=""
    let part2=""

 for (let i = 0; i < navigate_button.length; i++) {
  navigate_button[i].addEventListener("click", () => {
 
      var url = navigate_link[i].getAttribute('href')
      const parts = url.split("dir/");
      const firstPart = parts[0] + "dir/";
      part1=firstPart
      part2=parts[1]


    getLocation()
  });
}
function getLocation() {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(showPosition);
  } else {
    console.log("Geolocation is not supported by this browser.")
  }
}

function showPosition(position) {
  var fulCords = `${part1}${position.coords.latitude},${position.coords.longitude}/${part2}`
  console.log(fulCords);
  window.open(fulCords, '_blank');
}

const submit = document.getElementsByClassName("button--full")

submit.addE









