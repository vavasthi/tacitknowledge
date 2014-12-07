/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function createWordMap(url) {
    d3.json(url, function (json) {
        var fill = d3.scale.category20();

        d3.layout.cloud().size([1024, 1024])
                .words(json.map(function (d) {
                    return {text: d.word, size: d.sentiment *128};
                }))
                .rotate(function () {
                    return ~~(Math.random() * 2) * 90;
                })
                .font("Impact")
                .fontSize(function (d) {
                    return d.size;
                })
                .on("end", draw)
                .start();

        function draw(words) {
            d3.select("body").append("svg")
                    .attr("width", 1024).attr("height", 1024)
                    .append("g").attr("transform", "translate(480,480)")
                    .selectAll("text").data(words)
                    .enter().append("text")
                    .style("font-size", function (d) {
                        return d.size + "px";
                    })
                    .style("font-family", "Impact")
                    .style("fill", function (d, i) {
                        return fill(i);
                    })
                    .attr("text-anchor", "middle")
                    .attr("transform", function (d) {
                        return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")";
                    })
                    .text(function (d) {
                        return d.text;
                    });
        }
    })

}
