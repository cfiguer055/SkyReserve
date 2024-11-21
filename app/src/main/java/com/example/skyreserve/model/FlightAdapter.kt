package com.example.skyreserve.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skyreserve.R

class FlightAdapter(private val flightList: List<FlightInfo>, private val listener: OnFlightClickListener) : RecyclerView.Adapter<FlightAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Initialize all your TextViews from the layout
        val departureArrivalTimeTextView: TextView = view.findViewById(R.id.departureArrivalTimeTextView)
        val tripCostTextView: TextView = view.findViewById(R.id.tripCostTextView)

        val departureArrivalCityTextView: TextView = view.findViewById(R.id.departureArrivalCityTextView)
        val tripTypeTextView: TextView = view.findViewById(R.id.tripTypeTextView)

        val departureArrivalAirportTextView: TextView = view.findViewById(R.id.departureArrivalAirportTextView)

        val flightDurationTextView: TextView = view.findViewById(R.id.flightDurationTextView)

        val airlineTextView: TextView = view.findViewById(R.id.airlineTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.flight_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val flight = flightList[position]

        holder.departureArrivalTimeTextView.text = "${flight.departureTime} - ${flight.arrivalTime}"

        holder.departureArrivalCityTextView.text = "${flight.departureTime} - ${flight.arrivalTime}"
        holder.tripCostTextView.text = "${flight.price}"

        holder.departureArrivalCityTextView.text = "${flight.departureCity} - ${flight.arrivalCity}"
        holder.tripTypeTextView.text = "${flight.tripType}"

        holder.departureArrivalAirportTextView.text = "${flight.departureAirportCode} - ${flight.arrivalAirportCode}"

        holder.flightDurationTextView.text = "${flight.duration}"

        holder.airlineTextView.text = "${flight.airline}"

        // Set the whole item as clickable
        holder.itemView.setOnClickListener {
            // Handle the item click event
            listener.onFlightClick(flight)
        }
    }

    override fun getItemCount() = flightList.size

    interface OnFlightClickListener {
        fun onFlightClick(selectedFlightInfo: FlightInfo)
    }
}


