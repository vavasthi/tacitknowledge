/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function createAdjacencyGraph(baseurl, topic, phrase) {

    var width = 1280,
            height = 1024;
    var url = baseurl + "/" + topic + "/" + phrase
    var color = d3.scale.category20();

    var radius = d3.scale.sqrt()
            .range([0, 8]);

    var svg = d3.select("body").append("svg")
            .attr("width", width)
            .attr("height", height);

    var force = d3.layout.force()
            .size([width, height])
            .charge(-400)
            .linkDistance(function (d) {
                return 500 - 10 * Math.min(radius(d.source.size), radius(d.target.size));
            });

    d3.json(url, function (error, graph) {
        if (error)
            throw error;

        force
                .nodes(graph.nodes)
                .links(graph.links)
                .on("tick", tick)
                .start();

        var link = svg.selectAll(".link")
                .data(graph.links)
                .enter().append("g")
                .attr("class", "link");

        link.append("line")
                .style("stroke-width", function (d) {
                    return (d.bond * 2 - 1) * 2 + "px";
                });

        link.filter(function (d) {
            return d.bond > 1;
        }).append("line")
                .attr("class", "separator");

        var node = svg.selectAll(".node")
                .data(graph.nodes)
                .enter().append("g")
                .attr("class", "node")
                .on("click", function(d) {
                    d3.selectAll("svg > *").remove();
                    createAdjacencyGraph(baseurl, topic, d.name);
                })
                .call(force.drag);

        node.append("circle")
                .attr("r", function (d) {
                    return radius(d.size);
                })
                .style("fill", function (d) {
                    return color(d.name);
                });

        node.append("text")
                .attr("dy", ".35em")
                .attr("text-anchor", "middle")
                .text(function (d) {
                    return d.name;
                });

        function tick() {
            link.selectAll("line")
                    .attr("x1", function (d) {
                        return d.source.x;
                    })
                    .attr("y1", function (d) {
                        return d.source.y;
                    })
                    .attr("x2", function (d) {
                        return d.target.x;
                    })
                    .attr("y2", function (d) {
                        return d.target.y;
                    });

            node.attr("transform", function (d) {
                return "translate(" + d.x + "," + d.y + ")";
            });
        }
    });
}
